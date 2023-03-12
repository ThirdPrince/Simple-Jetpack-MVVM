package com.dhl.example.user.vm

import User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.example.dogs.model.DogBreed
import com.dhl.example.user.adapter.UserAdapter
import com.dhl.example.user.api.RetrofitManager
import kotlinx.coroutines.launch

/**
 * business logic for user
 * @author dhl
 * @date 2023 3.12
 */
class UserViewModel : ViewModel() {

    private val liveDataUser = MutableLiveData<List<User>>()

    private val useList = mutableListOf<User>()

    /**
     * 点击事件
     */
    val selectedItem = MutableLiveData<User>()


    val selectedText = MutableLiveData<User>()


    private val adapter = UserAdapter(this)

    init {
        adapter.setUsers(useList)
    }

    fun getUsers(): LiveData<List<User>> {

        viewModelScope.launch {
            val response = RetrofitManager.gitHubService.getUsers()
            liveDataUser.value = response
            refreshList(response)
        }

        return liveDataUser
    }

    private fun refreshList(users:List<User>){
        useList.addAll(users)
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

    private fun getUserByIndex(index: Int): User {
        return liveDataUser.value!![index]
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