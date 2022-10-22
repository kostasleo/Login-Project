package com.example.loginproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginproject.ui.theme.DarkGrayMe
import com.example.loginproject.ui.theme.LoginProjectTheme
import com.example.loginproject.ui.theme.MyColors
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.loginproject.ui.theme.GreenMe
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var notLoggedIn = false

        setContent {
            LoginProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if(notLoggedIn) {
                        LoginPage(context = applicationContext)
                    } else {
                        HomePage()
                    }
                }
            }
        }
    }
}

@Composable
fun HomePage() {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopLabel(type = "home")
    }
}

@Composable
fun LoginPage(colors: TextFieldColors = MyColors(), context: Context) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false)}

    Column(modifier = Modifier.fillMaxWidth()) {

        TopLabel("login")

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = DarkGrayMe
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(38.dp)
            ) {
                Spacer(modifier = Modifier.height(71.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                InputLabelWithInfo(type = stringResource(R.string.user_id_label))}

                TextField(
                    value = userId,
                    onValueChange = {userId = it},
                    placeholder = { Text(text = "Πληκτρολογήστε το UserID σας", color = Color.Gray)},
                    singleLine = true,
                    modifier = Modifier
                        .background(DarkGrayMe)
                        .fillMaxWidth()
                        .height(52.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = colors
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputLabelWithInfo(type = stringResource(R.string.password_label_gr))
                    TextButton(onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.padding(2.dp)) {
                        Text(
                            text = stringResource(R.string.show_password_label_gr),
                            style = TextStyle(textAlign = TextAlign.End),
                            color = GreenMe,
                        )
                    }
                }
                TextField(
                    value = password,
                    onValueChange = {password = it},
                    placeholder = { Text(text = "Πληκτρολογήστε τον Κωδικό σας", color = Color.Gray)},
                    singleLine = true,
                    modifier = Modifier
                        .background(DarkGrayMe)
                        .fillMaxWidth()
                        .height(52.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = colors,
                    visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onClick = { handleLogin(userId, password, context) },
                    border = BorderStroke(1.dp, GreenMe),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenMe),
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                ){
                    Text( text = stringResource(R.string.login_text_gr),
                            fontSize = 20.sp)
                }
            }
        }
    }
}

fun handleLogin(userId: String, password: String, context: Context) {
    if (userId == "1234" && password == "1234"){
        Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Log In Failed", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun TopLabel(type:String){
    var text:String = stringResource(R.string.login_text_gr)
    if(type == "home"){
        text = stringResource(R.string.home_text_label)
    }
    
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
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun InputLabelWithInfo(type: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(
            text = type, color = Color.White, fontSize = 22.sp,
            style = TextStyle(textAlign = TextAlign.Start),
            modifier = Modifier.padding(start = 10.dp),
        )

        IconButton(onClick = {}
//                  onClick = { Toast.makeText(context, "Info", Toast.LENGTH_SHORT).show()}
        ) {
            Image(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginProjectTheme {
        LoginPage(context = LocalContext.current)
    }
}