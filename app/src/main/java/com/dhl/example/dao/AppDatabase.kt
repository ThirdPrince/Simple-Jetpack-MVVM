package com.dhl.example.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dhl.example.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}