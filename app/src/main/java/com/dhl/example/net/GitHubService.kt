package com.dhl.example.net

import com.dhl.example.model.User
import retrofit2.http.GET

/**
 * Kotlin-Tutorials stars
 */
interface GitHubService {

     @GET("/repos/enbandari/Kotlin-Tutorials/stargazers")
     suspend fun getUsers():MutableList<User>

//     @GET("/repos/enbandari/Kotlin-Tutorials/stargazers")
//     suspend fun getUsers2():MutableList<User2>

}