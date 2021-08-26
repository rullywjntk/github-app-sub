package com.rullywjntk.mygithub.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rullywjntk.mygithub.data.db.FavUserDB
import com.rullywjntk.mygithub.data.db.FavUserDao
import com.rullywjntk.mygithub.data.db.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favUserDao: FavUserDao?
    private var favUserDB: FavUserDB? = FavUserDB.getDatabase(application)

    init {
        favUserDao = favUserDB?.favUserDao()
    }

    fun getUser(): LiveData<List<FavoriteUser>>? {
        return favUserDao?.getUser()
    }
}