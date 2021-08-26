package com.rullywjntk.mygithub.data

data class UserDetail(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val name: String,
    val followers_url: String,
    val following_url: String,
    val public_repos: String,
    val location: String,
    val company: String,
    val followers: String,
    val following: String
)