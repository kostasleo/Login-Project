package com.example.loginproject.ui.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.loginproject.ui.login.TopLabel
import com.example.loginproject.viewmodel.NavBarViewModel
import com.example.loginproject.viewmodel.Screen

@Composable
fun SettingsPage(navController: NavController) {

    Scaffold(
        topBar ={
            TopLabel(type = "settings") },
        bottomBar = {
            TabBar(navController = navController, navBarViewModel = NavBarViewModel())
        }) {
    }
}