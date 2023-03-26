package com.dhl.example.user.vm

import User
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.example.user.adapter.DynamicChangeCallBack
import com.dhl.example.user.adapter.UserAdapter
import com.dhl.example.user.api.RetrofitManager
import com.dhl.uimode.AppMode
import com.dhl.uimode.Mode
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*

/**
 * business logic for user
 * @author dhl
 * @date 2023 3.12
 */
class UserViewModel : ViewModel() {

    private val TAG = "UserViewModel"


    val userObservableArrayList = ObservableArrayList<User>()

    private val _liveDataUser = MutableLiveData<List<User>>()

    val liveDataUser: LiveData<List<User>>
        get() = _liveDataUser

    private val _liveDataLoading = MutableLiveData<Boolean>()

    val liveDataLoading: LiveData<Boolean>
        get() = _liveDataLoading

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error
    /**
     * 点击事件
     */
    val selectedItem = MutableLiveData<User>()


    val selectedText = MutableLiveData<User>()


    private val adapter = UserAdapter(this)

    init {
        _liveDataLoading.value = true

        userObservableArrayList.addOnListChangedCallback(DynamicChangeCallBack<User>(adapter))
    }

    private val exception = CoroutineExceptionHandler { _, throwable ->
        _error.value = throwable.message
        _liveDataLoading.value = false
        Log.e(TAG, throwable.message!!)
    }
    /**
     * 获取User
     */
    fun getUsers(): LiveData<List<User>> {

        viewModelScope.launch(exception) {
            val response = RetrofitManager.gitHubService.getUsers()
            _liveDataUser.value = response
            userObservableArrayList.clear()
            userObservableArrayList.addAll(response)
            refreshList(response)
        }

        return liveDataUser
    }

    /**
     * just Test
     */
    fun getUsersMore(): LiveData<List<User>> {

        viewModelScope.launch(exception) {
            val response = RetrofitManager.gitHubService.getUsers()
            _liveDataUser.value = response
            userObservableArrayList.addAll(response)
            refreshList(response)
        }

        return liveDataUser
    }

    private fun refreshList(users: List<User>) {
        _liveDataLoading.value = false

    }

    fun onItemClick(index: Int) {
        val user: User = getUserByIndex(index)
        selectedItem.value = user
    }


    fun onTextClick(index: Int) {
        val user: User = getUserByIndex(index)
        selectedText.value = user
    }

    /**
     * 点击Fb 更新主题颜色
     */
    fun onFbClick() {

        if (AppMode.currentMode == Mode.UIModeNight) {
            AppMode.update(Mode.UIModeDay)
        } else {
            AppMode.update(Mode.UIModeNight)
        }

    }

    private fun getUserByIndex(index: Int): User {
        return userObservableArrayList[index]
    }

    fun getUserAdapter(): UserAdapter {
        return adapter
    }


    fun getUserName(index: Int): String {
        return getUserByIndex(index).login
    }

    fun getUserUrl(index: Int): String {
        return getUserByIndex(index).avatar_url
    }


}