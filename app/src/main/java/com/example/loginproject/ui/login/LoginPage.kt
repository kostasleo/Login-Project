package com.example.loginproject.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginproject.R
import com.example.loginproject.navigation.Pages
import com.example.loginproject.ui.theme.DarkGrayMe
import com.example.loginproject.ui.theme.GreenMe
import com.example.loginproject.ui.theme.MyColors
import com.example.loginproject.viewmodel.LoginViewModel
import java.util.regex.Pattern

@Composable
fun LoginPage(colors: TextFieldColors = MyColors(),
              context: Context,
              viewModel: LoginViewModel,
              navController: NavController
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    var credentialError by rememberSaveable { mutableStateOf(false) }

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

                // UserId Input Label
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    InputLabelWithInfo(type = stringResource(R.string.user_id_label), 250)
                }

                // UserId Text Field
                TextField(
                    value = userId,
                    onValueChange = { userId = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.user_id_placeholder),
                            color = Color.Gray
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .background(DarkGrayMe)
                        .fillMaxWidth()
                        .height(52.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus()}),
                    colors = colors,
                    isError = credentialError
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Input Label
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InputLabelWithInfo(type = stringResource(R.string.password_label_gr), 0)
                    TextButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.show_password_label_gr),
                            style = TextStyle(textAlign = TextAlign.End),
                            color = GreenMe,
                        )
                    }
                }

                // Password Text Field
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.password_placeholder),
                            color = Color.Gray
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .background(DarkGrayMe)
                        .fillMaxWidth()
                        .height(52.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus()}
                    ),
                    colors = colors,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = credentialError
                )

                Spacer(modifier = Modifier.weight(1f))

                // Login Button
                OutlinedButton(
                    onClick = {
//                        credentialError = handleLogin(userId, password, context, viewModel, navController)
                        navController.navigate(Pages.Books.route)
                              },
                    border = BorderStroke(1.dp, GreenMe),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenMe),
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = stringResource(R.string.login_text_gr),
                        fontSize = 20.sp
                    )
                }

                // Credentials Error
                if(credentialError){
                    AlertDialog(onDismissRequest = { credentialError = false },
                        shape = RoundedCornerShape(size = 12.dp),
                        buttons = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)) {
                                Text(
                                    text = "Λανθασμένα στοιχεία",
                                    color = Color.White, fontSize = 22.sp,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontWeight = FontWeight.Bold),
                                    lineHeight = 21.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = "Έχετε υποβάλει λάθος στοιχεία.",
                                    color = Color.White, fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 21.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )}
                                TextButton(
                                    onClick = { credentialError = false },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                ) {
                                    Text(
                                        text = "Επιστροφή",
                                        color = GreenMe, fontSize = 20.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }

                        },
                        modifier = Modifier
//                            .padding(bottom = pad.dp)
                    )
                }
            }
        }
    }
}

fun handleLogin(userId: String,
                password: String,
                context: Context,
                viewModel: LoginViewModel,
                navController: NavController) : Boolean {
    val userIdPattern = "^[A-Z][A-Z][0-9]{4}\$"
    val isUserIdValid = Pattern.matches(userIdPattern, userId)

    val passwordPattern = "^(?=.*[!@#\$%^()-=+)&+=])(?=.*[A-Z].*[A-Z].*)(?=.*[a-z].*[a-z].*[a-z].*)(?=.*[0-9].*[0-9].*).{8,}$"
    val isPasswordValid = Pattern.matches(passwordPattern, password)

    if (isPasswordValid && isUserIdValid) {
        Toast.makeText(context, "Valid credentials", Toast.LENGTH_SHORT).show()
//        viewModel.uiState.value.isLogged = true
//        viewModel::doLogin

        viewModel.setUserId(userId)
        viewModel.setPassword(password)

        // make login request
//        viewModel.doLogin()
        if(userId == "TH1234" && password == "3NItas1!"){
            navController.navigate(Pages.Books.route)
            return false
        }
        // navigate to books
//        if(viewModel.isLogged()){
//      { launchSingleTop = true }
        return true
    } else {
        return true
    }
}

@Composable
fun TopLabel(type: String) {
    var text: String = stringResource(R.string.login_text_gr)
    if (type == "books") {
        text = stringResource(R.string.books_text_label)
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
fun InputLabelWithInfo(type: String, pad: Int) {
    var showInfo by remember { mutableStateOf(false) }

    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = type, color = Color.White, fontSize = 22.sp,
                style = TextStyle(textAlign = TextAlign.Start),
                modifier = Modifier.padding(start = 10.dp),
            )

            IconButton(onClick = { showInfo = !showInfo }
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

        if (showInfo) {
            var infoMessage: String = ""
            if (type == stringResource(R.string.password_label_gr)) {
                infoMessage = stringResource(R.string.password_info_gr)
            } else if (type == stringResource(R.string.user_id_label)) {
                infoMessage = stringResource(R.string.userid_info_gr)
            }
            AlertDialog(onDismissRequest = { showInfo = false },
                shape = RoundedCornerShape(size = 12.dp),
                buttons = {
                    TextButton(
                        onClick = { showInfo = false },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = infoMessage,
                            color = Color.White, fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.SansSerif,
                            style = TextStyle(fontWeight = FontWeight.Light),
                            lineHeight = 21.sp,
                        )
                    }
                },
                modifier = Modifier
                    .padding(bottom = pad.dp)
            )
        }
    }
}