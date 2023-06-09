package com.dhl.example.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dhl.example.model.User

/**
 * 数据库的查询与删除
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM USER ")
    fun getAll(): List<User>

    @Insert
    fun insertAll(users: List<User>)

    @Query("DELETE FROM User")
    fun deleteAll()
}