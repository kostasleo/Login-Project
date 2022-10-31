package com.example.loginproject.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun firstLoad() {
        _homeState.value.firstLoaded = true
    }

    fun reload() {
        homeState.value.activePage = 0
        _homeState.value.currentActivePage = "books"
        activePageName = "books"
        activePage = 0
    }

    fun updateActivePage(page: Int) {
        _homeState.value.activePage = page
        _homeState.value.currentActivePage = if (page==0) "books" else "settings"
        activePageName = if (page==0) "books" else "settings"
        activePage = page
    }
}