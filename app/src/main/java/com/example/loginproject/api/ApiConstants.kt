package com.example.loginproject.api

// Login Request Return class type
data class LoginReturn(
    var expires_in: Int,
    var token_type: String,
    var refresh_token: String,
    var access_token: String
)

object ApiConstants {
    const val BASE_URL = "https://3nt-demo-backend.azurewebsites.net/Access/"
    const val LOGIN_ENDPOINT = "Login"
    const val BOOKS_ENDPOINT = "Books"

    // access token hardcoded, used just for comparison
    const val TOKEN = "T1amGT21.Idup.298885bf38e99053dca3434eb59c6aa"
}