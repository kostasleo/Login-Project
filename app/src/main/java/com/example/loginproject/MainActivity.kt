package com.example.loginproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.loginproject.ui.theme.LoginProjectTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.loginproject.navigation.NavigationScreen
import com.example.loginproject.ui.login.LoginPage
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.LoginState
import com.example.loginproject.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val booksViewModel: BooksViewModel by viewModels()

    //private val loginState by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
//                    CompositionLocalProvider(LoginState provides loginState) {
                        NavigationScreen(
                            navController = navController,
                            loginViewModel = loginViewModel,
                            booksViewModel = booksViewModel
                        )
//                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginProjectTheme {
//        LoginPage(context = LocalContext.current, viewModel = AuthenticationViewModel(), navController = navC)
    }
}