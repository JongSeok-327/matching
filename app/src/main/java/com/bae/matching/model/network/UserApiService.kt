package com.bae.matching.model.network

import com.bae.matching.model.entities.UserDetailResponse
import com.bae.matching.model.entities.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService
{
    @GET("users.json")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("{USER_ID}/user.json")
    suspend fun getUserDetail(@Path("USER_ID") userId: Int): Response<UserDetailResponse>
}