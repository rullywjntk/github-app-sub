package com.rullywjntk.mygithub.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rullywjntk.mygithub.data.RetrofitConfig
import com.rullywjntk.mygithub.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(username: String){
        RetrofitConfig.service
            .getFollowers(username)
            .enqueue(object: Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }
}