package com.example.loginproject.navigation

sealed class Pages (val route: String) {
    object Login : Pages("login")
    object Home : Pages("home")
}