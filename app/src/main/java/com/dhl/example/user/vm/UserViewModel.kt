package com.dhl.example.user.vm

import User
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.example.user.adapter.UserAdapter
import com.dhl.example.user.api.RetrofitManager
import com.dhl.uimode.AppMode
import com.dhl.uimode.Mode
import kotlinx.coroutines.launch

/**
 * business logic for user
 * @author dhl
 * @date 2023 3.12
 */
class UserViewModel : ViewModel() {

    private  val TAG = "UserViewModel"

    val userList = mutableListOf<User>()

    private val _liveDataUser = MutableLiveData<List<User>>()

    val liveDataUser: LiveData<List<User>>
        get() = _liveDataUser

    private val _liveDataLoading = MutableLiveData<Boolean>()

    val liveDataLoading: LiveData<Boolean>
        get() = _liveDataLoading




    /**
     * 点击事件
     */
    val selectedItem = MutableLiveData<User>()


    val selectedText = MutableLiveData<User>()


    private val adapter = UserAdapter(this)

    init {
        _liveDataLoading.value = true
    }

    fun getUsers(): LiveData<List<User>> {

        viewModelScope.launch {
            val response = RetrofitManager.gitHubService.getUsers()
            _liveDataUser.value = response
            userList.clear()
            userList.addAll(response)
            refreshList(response)
        }

        return liveDataUser
    }

    private fun refreshList(users:List<User>){
        Log.e(TAG,"user--${users.toString()}")
        _liveDataLoading.value = false
        adapter.notifyDataSetChanged()
    }

    fun onItemClick(index: Int) {
        val user: User = getUserByIndex(index)
        selectedItem.value = user
    }


    fun onTextClick(index: Int) {
        val user: User = getUserByIndex(index)
        selectedText.value = user
    }

    fun onFbClick() {

        //AppMode.update(Mode.UIModeNight)
        if(AppMode.currentMode == Mode.UIModeNight ){
            AppMode.update(Mode.UIModeDay)
        }else{
            AppMode.update(Mode.UIModeNight)
        }

    }

    private fun getUserByIndex(index: Int): User {
        return userList[index]
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