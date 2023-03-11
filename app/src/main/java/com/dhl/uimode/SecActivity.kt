package com.dhl.uimode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dhl.uimode.databinding.ActivityMainBinding
import com.dhl.uimode.databinding.ActivitySecBinding

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)
        val binding = DataBindingUtil.setContentView<ActivitySecBinding>(this, R.layout.activity_sec)
        binding.lifecycleOwner = this
    }
}