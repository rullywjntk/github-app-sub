package com.rullywjntk.mygithub.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavUserDB : RoomDatabase() {

    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavUserDB? = null

        fun getDatabase(context: Context): FavUserDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavUserDB::class.java,
                    "fav_users_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}