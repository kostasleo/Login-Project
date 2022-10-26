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
//    val isLogged: Boolean = false,
//    val isLoading: Boolean = false,
//    val error: String? = null
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

//    fun login(userId: String, password: String){
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

//val LoginState = compositionLocalOf<LoginViewModel> { error("Login error")  }


// Authentication (from joebirch Authentication)

//data class AuthenticationState(
//    val userId: String? = null,
//    val password: String? = null,
//    val isLoading: Boolean = false,
//    val isLogged: Boolean = false,
//    val error: String? = null
//) {
//    fun isFormValid(): Boolean {
//        return userId?.isNotEmpty() == true && password?.isNotEmpty() == true
//    }
//}
//
//class AuthenticationViewModel : ViewModel() {
//    private val uiState = MutableStateFlow(AuthenticationState())
//
//    val isSuccessLoading = mutableStateOf(false)
//    val isLoggedTho = mutableStateOf(uiState.value.isLogged)
//
//    private fun toggleLogin(logged: Boolean) {
//        uiState.value = uiState.value.copy(
//            isLogged = logged
//        )
//    }
//
//    private fun updateUserId(userId: String) {
//        uiState.value = uiState.value.copy(
//            userId = userId
//        )
//    }
//
//    private fun updatePassword(password: String) {
//        uiState.value = uiState.value.copy(
//            password = password
//        )
//    }
//}
//
//sealed class AuthenticationEvent {
//    object ToggleAuthenticationMode: AuthenticationEvent()
//
//    class UserIdChanged(val userId: String): AuthenticationEvent()
//
//    class PasswordChanged(val password: String) : AuthenticationEvent()
//}
