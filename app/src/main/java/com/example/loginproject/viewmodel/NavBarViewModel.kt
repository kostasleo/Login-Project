package com.example.loginproject.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.loginproject.R
import com.example.loginproject.navigation.Pages
import com.example.loginproject.ui.theme.DarkGrayMe
import com.example.loginproject.ui.theme.GreenMe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class Screen(val route: String, @StringRes val resourseId: Int) {
    object Books : Screen("books", R.string.books)
    object Settings : Screen("settings", R.string.settings)
}

data class NavBarState(
    var currentActivePage: String ,
    var isActivePage: Boolean = false,
    var activePage: Int = 0,
    var tint: Color = DarkGrayMe
)

class NavBarViewModel: ViewModel() {

    private val _navBarState = MutableStateFlow(NavBarState(""))
    val navBarState: StateFlow<NavBarState> = _navBarState.asStateFlow()

    var activePage by mutableStateOf("books")
        private set

    val items = listOf(
        Screen.Books,
        Screen.Settings,
    )

    init {
        reload()
    }

    fun reload() {
        _navBarState.value = NavBarState("books", true)
    }

    private fun updateActivePage(page: Int) {
        navBarState.value.activePage = page
        navBarState.value.isActivePage = true
        navBarState.value.currentActivePage = if (page==0) "books" else "settings"
        navBarState.value.tint = GreenMe
    }
}