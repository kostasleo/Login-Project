package com.example.loginproject.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.loginproject.api.ApiConstants
import com.example.loginproject.api.ApiService
import com.example.loginproject.api.LoginReturn
import com.example.loginproject.navigation.Pages
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.regex.Pattern

data class LoginState(
    var UserName: String = "",
    var Password: String = "",
)

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    private val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    var isLoading by mutableStateOf(false)
    private var isSuccessLoading by mutableStateOf(false)
    private var isLoggedIn by mutableStateOf(false)
    private var result: Response<LoginReturn>? = null

    private fun setUserId(userId: String) {
        loginState.value.UserName = userId
    }

    private fun setPassword(password: String) {
        loginState.value.Password = password
    }

    private fun doLogin() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                isLoading = true        // set loading true on request start
                result = apiService.postLogin(loginState.value)
                isLoggedIn = true
                isLoading = false       // set loading false on request end
                isSuccessLoading = true
            } catch (err: Exception) {
                Log.d("login-err", err.message.toString())
            }
        }
    }

    suspend fun handleLogin(
        userId: String,
        password: String,
        viewModel: LoginViewModel,
        navController: NavController
    ): Boolean {

        // Verify credentials with Regular Expressions
        // RegEx userId has 2 uppercase letters and then 4 numbers
        val userIdPattern = "^[A-Z][A-Z][0-9]{4}\$"
        val isUserIdValid = Pattern.matches(userIdPattern, userId)

        // RegEx password has 1 specialChars, 2 upper case, 3 lower case, 2 numbers and length at least 8
        val passwordPattern =
            "^(?=.*[!@#\$%^()-=+)&+=])(?=.*[A-Z].*[A-Z].*)(?=.*[a-z].*[a-z].*[a-z].*)(?=.*[0-9].*[0-9].*).{8,}$"
        val isPasswordValid = Pattern.matches(passwordPattern, password)

        // if credential pattern is valid
        if (isPasswordValid && isUserIdValid) {
            viewModel.setUserId(userId)
            viewModel.setPassword(password)

            // make login request here
            viewModel.doLogin()

            // while loading wait for the request to complete
            if (viewModel.isLoading) {
                coroutineScope {
                    delay(2000)
                    if (!viewModel.isSuccessLoading)
                        delay(1000)
                }
            }

            // after loading check hardcoded the userId and password
            if (viewModel.isSuccessLoading) {
                if (userId == "TH1234" && password == "3NItas1!") {
                    // then compare the access token received with the one explected
                    if ((result?.body()?.access_token ?: String) == ApiConstants.TOKEN) {
                        navController.navigate(Pages.Home.route)
                        // returning false (credentialError) is success!
                        return false
                    }
                }
            }
            // returns error
            return true
        } else {
            // returns error
            return true
        }
    }
}
