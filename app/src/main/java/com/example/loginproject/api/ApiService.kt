package com.example.loginproject.api

import com.example.loginproject.api.ApiConstants.BASE_URL
import com.example.loginproject.api.ApiConstants.BOOKS_ENDPOINT
import com.example.loginproject.api.ApiConstants.LOGIN_ENDPOINT
import com.example.loginproject.viewmodel.Book
import com.example.loginproject.viewmodel.LoginState
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    // Api Calls
    @GET(BOOKS_ENDPOINT)
    suspend fun getBooks(@Header("Authorization") token: String): List<Book>

    @POST(LOGIN_ENDPOINT)
    suspend fun postLogin(@Body loginState: LoginState) : Response<LoginReturn>

    // Request builder using Retrofit
    companion object {
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}