package com.example.loginproject.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginproject.api.ApiConstants.BASE_URL
import com.example.loginproject.api.ApiConstants.BOOKS_ENDPOINT
import com.example.loginproject.api.ApiConstants.LOGIN_ENDPOINT
import com.example.loginproject.viewmodel.Book
import com.example.loginproject.viewmodel.LoginState
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET(BOOKS_ENDPOINT)
    suspend fun getBooks(@Header("Authorization") token: String): List<Book>

    @POST(LOGIN_ENDPOINT)
    suspend fun postLogin(@Body loginState: LoginState) : Response<ApiConstants.Token>

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