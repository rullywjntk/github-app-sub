package com.rullywjntk.mygithub.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fav_users")
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String
): Serializable