package com.bae.matching.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bae.matching.model.entities.UserResponse

@Dao
interface UserDao
{
    @Query("SELECT * FROM user_list")
    suspend fun getUserListData(): List<UserResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserListData(userListData: List<UserResponse>)

    @Query("DELETE FROM user_list")
    suspend fun deleteAllUserList()
}