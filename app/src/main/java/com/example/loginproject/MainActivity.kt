package com.example.loginproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginproject.ui.theme.LoginProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
                    color = Color.Green
                ) {
                    LoginPage()
                }
            }
        }
    }
}

@Composable
fun LoginPage() {
    var userId by remember { mutableStateOf("Πληκτρολογήστε το User ID σας") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp),
            color = Color.Black,
        ) {
            Text(
                modifier = Modifier
                    .height(41.dp)
                    .padding(top = 20.dp),
                text = "Σύνδεση",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFF111111)
        ) {
//            Image(painter = painterResource(R.drawable.bg_gradient), contentDescription = null)
            Column(modifier = Modifier.padding(20.dp)) {
                Spacer(modifier = Modifier.height(51.dp))
                TextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text("UserID", color = Color.Gray, fontSize = 24.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 10.dp)
                        .background(Color(0xFF111111))
                    )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginProjectTheme {
        LoginPage()
    }
}