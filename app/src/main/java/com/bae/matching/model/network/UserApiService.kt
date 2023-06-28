package com.bae.matching.model.network

import com.bae.matching.model.entities.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService
{
    @GET("users.json")
    suspend fun getUsers(): Response<List<UserResponse>>

}