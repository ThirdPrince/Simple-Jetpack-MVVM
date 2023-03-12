package com.dhl.example.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        userViewModel.selectedItem.observe(this, Observer {
            Toast.makeText(this,it.login+"被点击了",Toast.LENGTH_LONG).show()
        })

        userViewModel.selectedText.observe(this, Observer {
            Toast.makeText(this,it.login+"被点击了 Text",Toast.LENGTH_LONG).show()
        })

    }
}