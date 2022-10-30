package com.example.loginproject.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginproject.R
import com.example.loginproject.navigation.Pages
import com.example.loginproject.ui.login.TopLabel
import com.example.loginproject.ui.theme.ClearRippleTheme
import com.example.loginproject.ui.theme.DarkGrayMe
import com.example.loginproject.ui.theme.GreenMe
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.HomeViewModel
import com.example.loginproject.viewmodel.Screen

@Composable
fun HomePage(navController: NavController, homeViewModel: HomeViewModel, booksViewModel: BooksViewModel ) {
    LaunchedEffect(Unit) {
        homeViewModel.reload()
        if (!homeViewModel.homeState.value.firstLoaded ){//&& !booksViewModel.firstLoadingBooks){
            booksViewModel.getBooksList()
            Log.d("booksReq", "books request")
        }
//        currentOnTimeout()
//        homeViewModel.homeState.value.firstLoaded = true    // make func
        homeViewModel.firstLoad()
    }

//    if (booksViewModel.firstLoadingBooks && !homeViewModel.homeState.value.firstLoaded)
//        booksViewModel.deletePdfs()

    if(booksViewModel.isLoadingBooks){
        Text(
            text = stringResource(R.string.loading_label),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .padding(top = 200.dp)
                .fillMaxWidth()
            )
    } else {

    Scaffold(
        topBar = {
            TopLabel(type = homeViewModel.activePageName) },
        bottomBar = {
            TabBar(navController = navController, homeViewModel = homeViewModel) }
    ){
        if (homeViewModel.activePageName == "books"){
            BooksPage(navController = navController, bookViewModel = booksViewModel)
        } else if (homeViewModel.activePageName == "settings") {
            SettingsPage(navController = navController, homeViewModel = homeViewModel, booksViewModel = booksViewModel)
        }
    }
//        ) {
//        navController.navigate(Pages.Books.route) {launchSingleTop = true}
    }
}

@Composable
fun TabBar(navController: NavController, homeViewModel: HomeViewModel, ) {
//    var selectedPage by remember { mutableStateOf(homeViewModel.homeState.value.activePage) }
    var selectedPage by remember {
        mutableStateOf(0)
    }
    val items = listOf(
        Screen.Books,
        Screen.Settings
    )
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(104.dp)) {
        // Tabs Bg image and wave image
        Image(
            painter = painterResource(R.drawable.tabs_bg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Image(
            painter = painterResource(R.drawable.tabs_wave),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        CompositionLocalProvider(LocalRippleTheme provides ClearRippleTheme) {


            BottomNavigation(
                backgroundColor = Color.Transparent,
                modifier = Modifier.fillMaxSize(),
                elevation = 0.dp
            ) {

                BottomNavigationItem(
                    selected = selectedPage == 0,
                    selectedContentColor = GreenMe,
                    unselectedContentColor = DarkGrayMe,
                    onClick = {
                        selectedPage = 0
                        homeViewModel.updateActivePage(0)
//                    navController.navigate(Pages.Books.route) {launchSingleTop = true}
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_book),
                            contentDescription = null,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                    },
//                modifier = Modifier.clickable(
//                    interactionSource = MutableInteractionSource(),
//                    indication = null
                )
                BottomNavigationItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Image(
                            painter = painterResource(R.drawable.btn_pause),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
//                    tint = Color.White.copy(alpha =0.0f)
                        )
                    }
                )
                BottomNavigationItem(
                    selected = selectedPage == 1,
                    selectedContentColor = GreenMe,
                    unselectedContentColor = DarkGrayMe,
                    onClick = {
                        selectedPage = 1
                        homeViewModel.updateActivePage(1)
//                    navController.navigate(Pages.Settings.route){launchSingleTop = true}
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = null,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                    }
                )
            }
        }
    }
//    }
}