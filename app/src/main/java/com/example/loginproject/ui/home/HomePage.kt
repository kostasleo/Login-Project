package com.example.loginproject.ui.home

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.loginproject.navigation.Pages
import com.example.loginproject.ui.login.TopLabel
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.NavBarViewModel

@Composable
fun HomePage(navController: NavController, booksViewModel: BooksViewModel) {
    LaunchedEffect(Unit, block = {
        booksViewModel.getBooksList()
//        currentOnTimeout()
        Log.d("home", "home page loaded")
    })

    Scaffold(
//        topBar = {
//            TopLabel(type = "books") },
//        bottomBar = {
//            TabBar(navController = navController, navBarViewModel = NavBarViewModel()) }
//    ){
//            BooksPage(navController = navController, bookViewModel = booksViewModel)
//        }
        ) {
        navController.navigate(Pages.Books.route) {launchSingleTop = true}
    }
}