package com.example.loginproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.loginproject.ui.theme.LoginProjectTheme
import androidx.navigation.compose.rememberNavController
import com.example.loginproject.navigation.NavigationScreen
import com.example.loginproject.viewmodel.*

class MainActivity : ComponentActivity() {

    // initializing view models
    private val loginViewModel: LoginViewModel by viewModels()
    private val booksViewModel: BooksViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // initializing NavController
                    val navController = rememberNavController()
                    NavigationScreen(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        booksViewModel = booksViewModel,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}