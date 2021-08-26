package com.rullywjntk.mygithub.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavUserDao {

    @Insert
    suspend fun insertData(user: FavoriteUser)

    @Query("SELECT * FROM fav_users")
    fun getUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM fav_users WHERE fav_users.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM fav_users WHERE fav_users.id = :id")
    suspend fun deleteUser(id: Int): Int
}