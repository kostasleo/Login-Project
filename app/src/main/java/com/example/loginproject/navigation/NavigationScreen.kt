package com.example.loginproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.loginproject.ui.home.HomePage
import com.example.loginproject.ui.login.LoginPage
import com.example.loginproject.viewmodel.*

@Composable
fun NavigationScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    booksViewModel: BooksViewModel,
    homeViewModel: HomeViewModel
){
    val start = Pages.Login.route

    NavHost(
        navController = navController,
        startDestination = start)
    {
        composable(route = Pages.Login.route)
        {
            LoginPage(
                context = LocalContext.current,
                viewModel = loginViewModel,
                navController = navController
            )
        }
        composable(route = Pages.Home.route){
            HomePage(
                navController = navController,
                homeViewModel = homeViewModel,
                booksViewModel = booksViewModel
            )
        }
    }
}