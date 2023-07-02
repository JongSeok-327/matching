package com.bae.matching.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bae.matching.model.entities.UserResponse
import com.bae.matching.utils.DATABASE_NAME
import com.bae.matching.utils.DATABASE_VERSION

@Database(entities = [UserResponse::class], version = DATABASE_VERSION)
abstract class MatchingDatabase : RoomDatabase()
{
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MatchingDatabase? = null

        fun getDatabase(context: Context): MatchingDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchingDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}