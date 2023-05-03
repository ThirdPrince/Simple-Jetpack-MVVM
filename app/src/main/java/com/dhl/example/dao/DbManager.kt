package com.dhl.example.dao

import androidx.room.Room
import com.dhl.example.app.MyApplication

/**
 * Db 管理类
 */
object DbManager {

    val db by lazy {
        Room.databaseBuilder(
            MyApplication.context,
            AppDatabase::class.java, "user.db"
        ).build()
    }
}