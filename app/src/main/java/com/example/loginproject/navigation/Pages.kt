package com.example.loginproject.navigation

import androidx.navigation.ActivityNavigator

sealed class Pages (val route: String) {
    object Login : Pages("login")
    object Home : Pages("home")
    object Books : Pages("books")
    object Settings : Pages("settings")
}