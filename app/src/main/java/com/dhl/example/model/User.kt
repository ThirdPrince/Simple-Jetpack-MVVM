package com.dhl.example.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * GitHubUser model
 * @author dhl
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val primaryKey: Long,
    val login: String,
    @ColumnInfo(name = "user_id") val id: Long,
    val avatar_url: String
)