package com.bae.matching.model.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetailResponse(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "photo")
    val photo: String?,
    @field:Json(name = "age")
    val age: Int?,
    @field:Json(name = "address")
    val address: String?,
    @field:Json(name = "nickname")
    val nickname: String?,
    @field:Json(name = "tweet")
    val tweet: String?
)