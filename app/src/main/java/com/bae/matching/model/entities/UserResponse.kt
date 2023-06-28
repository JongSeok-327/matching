package com.bae.matching.model.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "photo")
    val photo: String?,
    @field:Json(name = "nickname")
    val nickname: String?
)