package com.dhl.uimode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.dhl.example.user.ui.UserActivity
import com.dhl.uimode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        Glide.with(this).load("https://avatars.githubusercontent.com/u/26602893?s=96&v=4").into(binding.image)

        binding.btnDefault.setOnClickListener { AppMode.update(Mode.UIModelDefault) }
        binding.btnDay.setOnClickListener { AppMode.update(Mode.UIModeDay)

        }
        binding.btnNight.setOnClickListener {
            AppMode.update(Mode.UIModeNight)
        }
        binding.btn.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }



    }
}