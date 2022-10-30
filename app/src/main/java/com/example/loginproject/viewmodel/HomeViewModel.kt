package com.example.loginproject.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.loginproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class Screen(val route: String, @StringRes val resourseId: Int) {
    object Books : Screen("books", R.string.books)
    object Settings : Screen("settings", R.string.settings)
}

data class HomeState(
    var currentActivePage: String ,
    var activePage: Int = 0,
    var firstLoaded: Boolean = false
)

class HomeViewModel: ViewModel() {

    private val _homeState = MutableStateFlow(HomeState("books"))
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    var activePageName by mutableStateOf(_homeState.value.currentActivePage)
        private set

    var activePage by mutableStateOf(_homeState.value.activePage)
        private set

    var notificationsOn by mutableStateOf(true)

//    val items = listOf(
//        Screen.Books,
//        Screen.Settings,
//    )

//    init {
//        reload()
//    }

    fun reload() {
//        _homeState.value = HomeState("books", 0)
        homeState.value.activePage = 0              // here _ ?
        _homeState.value.currentActivePage = "books"
        activePageName = "books"
        activePage = 0
//        Log.d("reload", "reload")
//        _homeState.value.firstLoaded = true
    }

    fun updateActivePage(page: Int) {
        _homeState.value.activePage = page
        _homeState.value.currentActivePage = if (page==0) "books" else "settings"
        activePageName = if (page==0) "books" else "settings"
        activePage = page
        Log.d("updateActivePage", "active page changed to $page")
    }

    fun firstLoad() {
        _homeState.value.firstLoaded = true
    }
}