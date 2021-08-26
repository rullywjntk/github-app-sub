package com.rullywjntk.mygithub.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rullywjntk.mygithub.data.RetrofitConfig
import com.rullywjntk.mygithub.data.User
import com.rullywjntk.mygithub.data.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(search: String) {
        RetrofitConfig.service
            .getQuery(search)
            .enqueue(object : Callback<UserList> {
                override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}