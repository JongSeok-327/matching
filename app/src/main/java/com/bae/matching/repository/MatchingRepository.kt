package com.bae.matching.repository

import com.bae.matching.model.database.MatchingDatabase
import com.bae.matching.model.entities.UserResponse
import com.bae.matching.model.network.UserApiService
import java.lang.Exception

class MatchingRepository(
    private val apiService: UserApiService,
    private val database: MatchingDatabase
)
{
    suspend fun fetchUserData(): List<UserResponse> {
        val localData = database.userDao().getUserListData()
        return localData.ifEmpty {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                val newData = response.body() ?: emptyList()
                database.userDao().insertUserListData(newData)

                newData
            } else {
                throw Exception("Failed Fetch data")
            }
        }
    }

    suspend fun fetchUserDataFromDatabase(): List<UserResponse> {
        return database.userDao().getUserListData()
    }

    suspend fun fetchUserDataFromRemote(): List<UserResponse> {
        database.userDao().deleteAllUserList()
        return fetchUserData()
    }
}