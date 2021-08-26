package com.rullywjntk.mygithub.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rullywjntk.mygithub.data.RetrofitConfig
import com.rullywjntk.mygithub.data.UserDetail
import com.rullywjntk.mygithub.data.db.FavUserDB
import com.rullywjntk.mygithub.data.db.FavUserDao
import com.rullywjntk.mygithub.data.db.FavoriteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val detailUser = MutableLiveData<UserDetail>()

    private var favUserDao: FavUserDao?
    private var favUserDB: FavUserDB? = FavUserDB.getDatabase(application)

    init {
        favUserDao = favUserDB?.favUserDao()
    }

    fun setDetailUser(username: String) {
        RetrofitConfig.service
            .userDetail(username)
            .enqueue(object : Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getDetailUser(): LiveData<UserDetail> {
        return detailUser
    }

    fun addFavorite(id: Int, login: String, avatar: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(id, login, avatar)
            favUserDao?.insertData(user)
        }
    }

    suspend fun checkUser(id: Int) = favUserDao?.checkUser(id)
    fun deleteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favUserDao?.deleteUser(id)
        }
    }
}