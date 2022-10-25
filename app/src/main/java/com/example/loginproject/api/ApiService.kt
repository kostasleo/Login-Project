package com.example.loginproject.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginproject.api.ApiConstants.BASE_URL
import com.example.loginproject.api.ApiConstants.BOOKS_ENDPOINT
import com.example.loginproject.viewmodel.Book
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiService {

    @GET(BOOKS_ENDPOINT)
    suspend fun getBooks(@Header("Authorization") token: String): List<Book>

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

//interface ApiService {
//    @GET("todos")
//    suspend fun getTodos(): List<Todo>
//
//    companion object {
//        var apiService: ApiService? = null
//        fun getInstance(): ApiService {
//            if (apiService == null) {
//                apiService = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build().create(ApiService::class.java)
//            }
//            return apiService!!
//        }
//    }
//}
//
//
//data class Todo(
//    var userId: Int,
//    var id: Int,
//    var title: String,
//    var completed: Boolean
//)
//
//class TodoViewModel : ViewModel() {
//    private val _todoList = mutableStateListOf<Todo>()
//    var errorMessage: String by mutableStateOf("")
//    val todoList: List<Todo>?
//        get() = _todoList
//
//    fun getTodoList() {
//        viewModelScope.launch {
//            val apiService = ApiService.getInstance()
//            try {
//                _todoList.clear()
//                _todoList.addAll(apiService.getTodos())
//
//            } catch (e: Exception) {
//                errorMessage = e.message.toString()
//            }
//        }
//    }
//}