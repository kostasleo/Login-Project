package com.example.loginproject.api

import com.google.gson.annotations.SerializedName

object ApiConstants {
    const val BASE_URL = "https://3nt-demo-backend.azurewebsites.net/Access/"
    const val LOGIN_ENDPOINT = "Login"
    const val BOOKS_ENDPOINT = "Books"

    data class Token(@SerializedName("access_token")
    val accessToken:String)
    const val TOKEN = "T1amGT21.Idup.298885bf38e99053dca3434eb59c6aa"
}