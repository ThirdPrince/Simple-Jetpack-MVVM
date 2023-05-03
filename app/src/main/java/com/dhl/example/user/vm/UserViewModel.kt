package com.dhl.example.user.vm

import android.nfc.Tag
import com.dhl.example.model.User
import android.util.Log
import android.view.Display
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.example.dao.DbManager
import com.dhl.example.user.adapter.DynamicChangeCallBack
import com.dhl.example.user.adapter.UserAdapter
import com.dhl.example.user.api.RetrofitManager
import com.dhl.uimode.AppMode
import com.dhl.uimode.Mode
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * business logic for user
 * @author dhl
 * @date 2023 3.12
 */
class UserViewModel : ViewModel() {

    private val TAG = "UserViewModel"


    val userObservableArrayList = ObservableArrayList<User>()

    private val _liveDataUser = MutableLiveData<List<User>>()

    private val liveDataUser: LiveData<List<User>>
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
    fun getUsers(isLoadDb: Boolean = true): LiveData<List<User>> {

        viewModelScope.launch(exception) {
            if (isLoadDb) {
                var users = getUsersFromDb()
                userObservableArrayList.addAll(users)
            }
            val response = RetrofitManager.gitHubService.getUsers()
            _liveDataUser.value = response
            userObservableArrayList.clear()
            userObservableArrayList.addAll(response)
            Log.e(TAG, "刷新页面")
            refreshList(response)
            insertDb(response)
            Log.e(TAG, "操作数据完成")
        }

        return liveDataUser
    }

    fun getUsersByDb(): LiveData<List<User>> {

        viewModelScope.launch(exception) {

            var users = getUsersFromDb()
            userObservableArrayList.addAll(users)
            refreshList(users)
        }

        return liveDataUser
    }

    /**
     * 异步查询数据库
     */
    private suspend fun getUsersFromDb(): List<User> {
        var users: MutableList<User>
        withContext(Dispatchers.IO) {
            val userDao = DbManager.db.userDao()
            users = userDao.getAll() as MutableList<User>
        }
        return users
    }

    /**
     * 更新数据库
     */
    private suspend fun insertDb(users: List<User>) {

        if (users.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                val userDao = DbManager.db.userDao()
                userDao.deleteAll()
                userDao.insertAll(users)
                Log.e(TAG, "操作数据库")
            }
        }


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

    fun getUserByIndex(index: Int): User {
        return userObservableArrayList[index]
    }

    fun getUserAdapter(): UserAdapter {
        return adapter
    }


}