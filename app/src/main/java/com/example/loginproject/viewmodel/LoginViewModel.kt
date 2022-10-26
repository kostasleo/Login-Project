package com.example.loginproject.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginproject.api.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class LoginState(
    var userId: String,
    var password: String,
)

class LoginViewModel : ViewModel() {

    private val loginState = MutableStateFlow(LoginState("", ""))

    private val isSuccessLoading = mutableStateOf(value = false)
    private val isLoggedIn = mutableStateOf(value = false)
    private var isLoading = mutableStateOf(value = false)

    fun isLogged() : Boolean {
        return isLoggedIn.value
    }

    fun setUserId(userId: String) {
        loginState.value.userId = userId
    }

    fun setPassword(password: String) {
        loginState.value.password = password
    }

    fun isSuccessLoading() : Boolean {
        return isSuccessLoading.value
    }

    fun setSuccessLoading(result : Boolean) {
        isSuccessLoading.value = result
    }

    fun doLogin() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val token = apiService.postLogin(loginState.value)
                isLoggedIn.value = true
                Log.e("token", "$token")
            } catch (err: Exception) {
                Log.e("login-err", err.message.toString())
            }
        }
    }

    fun doLogout() {
        isLoggedIn.value = false
    }
}
