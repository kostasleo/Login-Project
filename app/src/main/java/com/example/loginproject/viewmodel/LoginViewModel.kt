package com.example.loginproject.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val isSuccessLoading = mutableStateOf(value = true)
    val isLoggedIn = mutableStateOf(value = false)

    fun setSuccessLoading(result : Boolean) {
        isSuccessLoading.value = result
    }

//    fun login(userId: String, password: String){
    fun doLogin(){
//        viewModelScope.launch(Dispatchers.IO) {
            if(isSuccessLoading.value){
                isLoggedIn.value = true
            }
//        }
    }


}


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
