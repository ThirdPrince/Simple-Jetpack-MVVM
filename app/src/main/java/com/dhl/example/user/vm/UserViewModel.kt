package com.dhl.example.user.vm

import User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.example.user.adapter.UserAdapter
import com.dhl.example.user.api.RetrofitManager
import kotlinx.coroutines.launch

/**
 * business logic for user
 * @author dhl
 */
class UserViewModel: ViewModel() {

    private val liveDataUser  = MutableLiveData<List<User>>()


    private val adapter = UserAdapter(this)

    fun getUsers():LiveData<List<User>>{

        viewModelScope.launch {
            val response = RetrofitManager.gitHubService.getUsers()
            liveDataUser.value = response
            adapter.setUsers(response)
        }

        return liveDataUser
    }

    private fun getUserByIndex(index: Int): User {
        return liveDataUser.value!![index]
    }

    fun getUserAdapter():UserAdapter{
        return adapter
    }


    fun getUserName(index: Int): String {
        return getUserByIndex(index).login
    }

    fun getUserUrl(index: Int): String {
        return getUserByIndex(index).avatar_url
    }



}