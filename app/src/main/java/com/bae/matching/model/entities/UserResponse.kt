package com.bae.matching.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bae.matching.utils.USER_LIST_TABLE_NAME
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = USER_LIST_TABLE_NAME)
@JsonClass(generateAdapter = true)
data class UserResponse(
    @PrimaryKey(autoGenerate = false)
    @field:Json(name = "id")
    val id: Int,
    @ColumnInfo(name = "photo")
    @field:Json(name = "photo")
    val photo: String?,
    @ColumnInfo(name = "nickname")
    @field:Json(name = "nickname")
    val nickname: String?
)