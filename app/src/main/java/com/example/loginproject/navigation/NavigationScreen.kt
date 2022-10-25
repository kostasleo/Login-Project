package com.example.loginproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.loginproject.ui.home.BooksPage
import com.example.loginproject.ui.login.LoginPage
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.LoginViewModel

@Composable
fun NavigationScreen(viewModel: LoginViewModel, navController: NavHostController){

    NavHost(navController = navController, startDestination = Pages.Books.route) {
        composable(route = Pages.Login.route) {
            LoginPage(context = LocalContext.current, viewModel = LoginViewModel(), navController = navController)
        }
        composable(route = Pages.Books.route) {
//            if(viewModel.isLoggedIn.value){
                BooksPage(navController = navController, bookViewModel = BooksViewModel())
//            } else {
//                navController.navigate(route = Pages.Login.route)
//            }
        }
    }
}