package com.example.loginproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginproject.R
import com.example.loginproject.ui.login.TopLabel
import com.example.loginproject.ui.theme.ClearRippleTheme
import com.example.loginproject.ui.theme.DarkGrayMe
import com.example.loginproject.ui.theme.GreenMe
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.HomeViewModel

@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel,
    booksViewModel: BooksViewModel
) {
    // on home page load, set book page as active
    LaunchedEffect(Unit) {
        homeViewModel.reload()

        // on first load, make books request
        if (!homeViewModel.homeState.value.firstLoaded) {
            booksViewModel.getBooksList()
        }
        homeViewModel.firstLoad()       // first load done
    }

    // loading page until books are loaded
    if (booksViewModel.isLoadingBooks) {
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
        // home page having top and bottom bar according to active page
        Scaffold(
            topBar = {
                TopLabel(type = homeViewModel.activePageName)
            },
            bottomBar = {
                TabBar(homeViewModel = homeViewModel)
            }
        ) {
            // decide which page to show
            if (homeViewModel.activePageName == "books") {
                BooksPage(
                    bookViewModel = booksViewModel
                )
            } else if (homeViewModel.activePageName == "settings") {
                SettingsPage(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    booksViewModel = booksViewModel
                )
            }
        }
    }
}

@Composable
fun TabBar(homeViewModel: HomeViewModel) {
    var selectedPage by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
    ) {
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

        // Bottom Navigation Items
        // Custom Theme to avoid Ripple Effect onClick (not working)
        CompositionLocalProvider(LocalRippleTheme provides ClearRippleTheme) {

            BottomNavigation(
                backgroundColor = Color.Transparent,
                modifier = Modifier.fillMaxSize(),
                elevation = 0.dp
            ) {

                // books Page Button
                BottomNavigationItem(
                    selected = selectedPage == 0,
                    selectedContentColor = GreenMe,
                    unselectedContentColor = DarkGrayMe,
                    onClick = {
                        selectedPage = 0
                        homeViewModel.updateActivePage(0)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_book),
                            contentDescription = null,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                    }
                )

                // Play/Pause Button
                val buttonImages = listOf(R.drawable.btn_play, R.drawable.btn_pause)
                var buttonImage by remember { mutableStateOf(0) }
                BottomNavigationItem(
                    selected = true,
                    onClick = { buttonImage = if (buttonImage == 0) 1 else 0 },
                    icon = {
                        Image(
                            painter = painterResource(buttonImages[buttonImage]),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                )

                // Settings Button
                BottomNavigationItem(
                    selected = selectedPage == 1,
                    selectedContentColor = GreenMe,
                    unselectedContentColor = DarkGrayMe,
                    onClick = {
                        selectedPage = 1
                        homeViewModel.updateActivePage(1)
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
}