package com.dhl.uimode

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.dhl.example.dogs.DogBreedsActivity
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

           // binding.image.colorFilter = null // 如果想恢复彩色显示，设置为null即可
        }
        binding.btnNight.setOnClickListener {
            AppMode.update(Mode.UIModeNight)
//            val cm = ColorMatrix()
//            cm.setSaturation(0f) // 设置饱和度
//
//            val grayColorFilter = ColorMatrixColorFilter(cm)
//            binding.image.colorFilter = grayColorFilter // 如果想恢复彩色显示，设置为null即可
        }
        binding.btn.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }



    }
}