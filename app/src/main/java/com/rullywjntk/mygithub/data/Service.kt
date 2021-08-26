package com.rullywjntk.mygithub.data

import com.rullywjntk.mygithub.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("search/users")
    @Headers(BuildConfig.API_KEY)
    fun getQuery(
        @Query("q") query: String
    ):Call<UserList>

    @GET("users/{username}")
    @Headers(BuildConfig.API_KEY)
    fun userDetail(
        @Path("username") username: String
    ):Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers(BuildConfig.API_KEY)
    fun getFollowers(
        @Path("username") username: String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers(BuildConfig.API_KEY)
    fun getFollowing(
        @Path("username") username: String
    ):Call<ArrayList<User>>

}