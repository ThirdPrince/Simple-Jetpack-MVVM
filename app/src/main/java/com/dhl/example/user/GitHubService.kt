package com.dhl.example.user

import User
import android.os.UserManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 * Kotlin-Tutorials stars
 */
interface GitHubService {


     @GET("/repos/enbandari/Kotlin-Tutorials/stargazers")
     suspend fun getUsers():List<User>

}