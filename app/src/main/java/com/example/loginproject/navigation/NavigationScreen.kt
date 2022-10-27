package com.example.loginproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginproject.ui.home.BooksPage
import com.example.loginproject.ui.home.SettingsPage
import com.example.loginproject.ui.login.LoginPage
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.LoginState
import com.example.loginproject.viewmodel.LoginViewModel

@Composable
fun NavigationScreen(navController: NavHostController,
                     loginViewModel: LoginViewModel,
                     booksViewModel: BooksViewModel)
{
    val loggedIn = loginViewModel.isLogged()

    val start = if(loggedIn) Pages.Books.route else Pages.Login.route

    NavHost(navController = navController,
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
        composable(route = Pages.Books.route) {
            BooksPage(
                navController = navController,
                bookViewModel = booksViewModel)
        }
        composable(route = Pages.Settings.route) {
            SettingsPage(navController = navController)
        }
    }
}