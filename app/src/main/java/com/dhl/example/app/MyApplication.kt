package com.dhl.example.app

import android.app.Application
import android.content.Context

/**
 * 程序入口
 */
class MyApplication:Application() {

    companion object {
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}