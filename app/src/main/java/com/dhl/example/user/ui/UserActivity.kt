package com.dhl.example.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dhl.example.user.vm.UserViewModel
import com.dhl.uimode.R
import com.dhl.uimode.databinding.ActivityUserBinding

/**
 * UserUi
 * @author dhl
 */
class UserActivity : AppCompatActivity() {

    private val userViewModel:UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val binding = DataBindingUtil.setContentView<ActivityUserBinding>(this,R.layout.activity_user)
        binding.userViewModel = userViewModel
        userViewModel.getUsers()
    }
}