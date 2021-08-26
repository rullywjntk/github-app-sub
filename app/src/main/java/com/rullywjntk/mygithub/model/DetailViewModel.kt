package com.rullywjntk.mygithub.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rullywjntk.mygithub.data.RetrofitConfig
import com.rullywjntk.mygithub.data.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val detailUser = MutableLiveData<UserDetail>()

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
}