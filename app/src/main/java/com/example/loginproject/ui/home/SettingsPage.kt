package com.example.loginproject.ui.home

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginproject.R
import com.example.loginproject.navigation.Pages
import com.example.loginproject.ui.theme.GreenMe
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.viewmodel.HomeViewModel

@Composable
fun SettingsPage(
    navController: NavController,
    homeViewModel: HomeViewModel,
    booksViewModel: BooksViewModel
) {
    Column(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
        SettingItem(homeViewModel,type = "notification",
            onClickAction = { homeViewModel.notificationsOn = !homeViewModel.notificationsOn })
        SettingItem(homeViewModel,type = "delete",
            onClickAction = { booksViewModel.deletePdfs() })
        SettingItem(homeViewModel,type = "logout",
            onClickAction = {navController.navigate(Pages.Login.route)})
    }
}

@Composable
fun SettingItem(homeViewModel: HomeViewModel,type: String, onClickAction: () -> Unit) {

    var text: String = ""
    when (type) {
        "notification" -> text = stringResource(R.string.notifications_label)
        "delete" -> text = stringResource(R.string.delete_books_label)
        "logout" -> text = stringResource(R.string.logout_label)
    }

    Spacer(modifier = Modifier.height(20.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        TextButton(onClick = onClickAction) {
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier
                    .height(25.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (type == "notification") {
            // custom switch button for notification label
            CustomSwitchButton(
                switchPadding = 5.dp,
                buttonWidth = 69.dp,
                buttonHeight = 40.dp,
                value = homeViewModel.notificationsOn,
                onClickAction = {
                    homeViewModel.notificationsOn = !homeViewModel.notificationsOn
                })
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    if (type != "logout")
        Divider(startIndent = 1.dp, thickness = 1.dp, color = Color.Gray)
}

@Composable
fun CustomSwitchButton(
    switchPadding: Dp,
    buttonWidth: Dp,
    buttonHeight: Dp,
    value: Boolean,
    onClickAction: () -> Unit
) {

    val switchSize by remember {
        mutableStateOf(buttonHeight - switchPadding * 2)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var switchClicked by remember {
        mutableStateOf(value)
    }

    var padding by remember {
        mutableStateOf(0.dp)
    }

    padding = if (switchClicked) buttonWidth - switchSize - switchPadding * 2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (switchClicked) padding else 0.dp,
        tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // Switch Button UI
    Box(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .background(if (switchClicked) GreenMe else Color.LightGray)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                switchClicked = !switchClicked
                onClickAction()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(switchPadding)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent)
            )

            Box(
                modifier = Modifier
                    .size(switchSize)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }
    }
}
