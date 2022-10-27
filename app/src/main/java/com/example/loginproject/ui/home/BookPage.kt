package com.example.loginproject.ui.home

import android.annotation.SuppressLint
import android.graphics.Color.alpha
import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.loginproject.ui.login.LoginPage
import com.example.loginproject.ui.login.TopLabel
import com.example.loginproject.ui.theme.DarkGrayMe
import com.example.loginproject.R
import com.example.loginproject.navigation.Pages
import com.example.loginproject.ui.theme.GreenMe
import com.example.loginproject.ui.theme.LoginProjectTheme
import com.example.loginproject.viewmodel.*
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BooksPage(navController: NavController, bookViewModel: BooksViewModel) {

    var showLoadingPage by remember { mutableStateOf(true)}

    LaunchedEffect(Unit, block = {
        bookViewModel.getBooksList()
//        currentOnTimeout()
    })
    
    Scaffold(
        topBar = {
            TopLabel(type = "books") },
        bottomBar = {
            TabBar(navController = navController, navBarViewModel = NavBarViewModel()) }
    ){
        LazyBooks(bookViewModel = bookViewModel)
    }
}

@Composable
fun TabBar(navController: NavController, navBarViewModel: NavBarViewModel) {
    var selectedPage by remember { mutableStateOf(0) }
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
        BottomNavigation(
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            elevation = 0.dp
        ){

                BottomNavigationItem(
                    selected = selectedPage == 0,
                    selectedContentColor = GreenMe,
                    unselectedContentColor = DarkGrayMe,
                    onClick = { navController.navigate(Pages.Books.route) {launchSingleTop = true} },
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
                    onClick = { navController.navigate(Pages.Settings.route){launchSingleTop = true} },
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
//    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyBooks(bookViewModel: BooksViewModel) {

    val booksList = bookViewModel.booksList

    Column(modifier = Modifier.padding(start = 38.dp, end = 18.dp)) {

        if (booksList != null) {
            LazyVerticalGrid(
                GridCells.Adaptive(140.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                item(span = {GridItemSpan(2)}) {
                    YearLabel(year = "2020")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList,"2020")) { book ->
                    Book(bookViewModel, book)
                }
                item(span = { GridItemSpan(2)}){
                    Spacer(modifier = Modifier.height(38.dp))
                }

                item(span = { GridItemSpan(2)}) {
                    YearLabel(year = "2019")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList,"2019")) { book ->
                    Book(bookViewModel, book)
                }

                item(span = { GridItemSpan(2)}) {
                    YearLabel(year = "2018")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList,"2018")) { book ->
                    Book(bookViewModel, book)
                }

                item(span = { GridItemSpan(2)}) {
                    YearLabel(year = "2010")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList,"2010")) { book ->
                    Book(bookViewModel, book)
                }
          }
        } else {
            Text(bookViewModel.errorMessage)
        }
        YearLabel(year = "2022")
    }
}

@Composable
fun YearLabel(year:String) {
    Text(
        text = year,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 38.dp)
    )
}

@Composable
fun Book(bookViewModel: BooksViewModel, book: Book) {

    var bookState by rememberSaveable {
        mutableStateOf(bookViewModel.changeBookState(book,BookState.DEFAULT))}

    val num = (0..9).random()
    val urls = listOf(
        stringResource(R.string.pdf_image_1),
        stringResource(R.string.pdf_image_2),
        stringResource(R.string.pdf_image_3),
        stringResource(R.string.pdf_image_4),
        stringResource(R.string.pdf_image_5),
        stringResource(R.string.pdf_image_6),
        stringResource(R.string.pdf_image_7),
        stringResource(R.string.pdf_image_8),
        stringResource(R.string.pdf_image_9),
        stringResource(R.string.pdf_image_10),
    )

    val pdfImage by rememberSaveable{ mutableStateOf(num) }

    Column(
        modifier = Modifier
            .height(230.dp)
            .width(140.dp)
    )
    {
        Box(modifier = Modifier){
            Image(
                painter = rememberAsyncImagePainter(urls[pdfImage]),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(180.dp)
                    .width(140.dp)
            )
            val scope = rememberCoroutineScope()
            if (bookState == BookState.DEFAULT) {
                IconButton(
                    onClick = {
                        scope.launch {
                            bookState = bookViewModel.changeBookState(book, BookState.DOWNLOADING1)
                            delay(1200)
                            bookState = bookViewModel.changeBookState(book, BookState.DOWNLOADING2)
                            delay(1200)
                            bookState = bookViewModel.changeBookState(book, BookState.DOWNLOADED)
                        }
                    },
                    modifier = Modifier
                        .padding(top = 70.dp, start = 45.dp)
                        .height(50.dp)
                        .width(50.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_download),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp),
                        tint = Color.White
                    )
                }
            }
            if (book.state == BookState.DOWNLOADING1) {
                val image = painterResource(R.drawable.downloading1)
                Box(modifier = Modifier.padding(top = 166.dp)) {
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier
                            .height(14.dp)
                            .width(140.dp)
                    )
                }
            }
            if (book.state == BookState.DOWNLOADING2) {
                val image = painterResource(R.drawable.downloading2)
                Box(modifier = Modifier.padding(top = 166.dp)) {
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier
                            .height(14.dp)
                            .width(140.dp)
                    )
                }
            }
            if (book.state == BookState.DOWNLOADED) {
                Box(modifier = Modifier.padding(top = 130.dp, start = 91.dp)){
                    Image(
                        painter = painterResource(R.drawable.downloaded_bg),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                    Box(modifier = Modifier.padding(top = 26.dp, start = 24.dp)){

                    Icon(
                        painter = painterResource(R.drawable.ic_check_w),
                        contentDescription = null,
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        tint = Color.White,
                    )
                    }

                }
            }
        }

        Text(
            text = book.title,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 10.dp),
            color = Color.White,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LoginProjectTheme {
//        BooksPage(navController = rememberNavController(), bookViewModel = BooksViewModel())
//        Book(bookViewModel = BooksViewModel(), book = Book(bookViewModel.))
    }
}


@Composable
fun TabBar1(navController: NavController, navBarViewModel: NavBarViewModel) {
    // state of pressed button
    var selectedPage by remember { mutableStateOf(0) }

    Surface(
        color = Color.White.copy(alpha =0.0f),
        modifier = Modifier
            .height(105.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier){
            // Tabs Bg image and wave image
            Image(painter = painterResource(R.drawable.tabs_bg),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth())
            Image(painter = painterResource(R.drawable.tabs_wave),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth())

            // Buttons

            IconButton(
                onClick = {  },
                modifier = Modifier.padding(top = 40.dp, start = 40.dp))
            {
                Icon(
                    painter = painterResource(R.drawable.ic_book),
                    contentDescription = null,
                    tint = GreenMe,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(top = 18.dp, start = 160.dp)
                    .size(80.dp))
            {
                Image(
                    painter = painterResource(R.drawable.btn_pause),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
//                    tint = Color.White.copy(alpha =0.0f)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 40.dp, start = 300.dp))
            {
                Icon(
                    painter = painterResource(R.drawable.ic_settings_sel),
                    contentDescription = null,
                    tint = DarkGrayMe,
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
        }
    }
}



//    Column(modifier = Modifier.fillMaxSize()) {
//
//        TopLabel(type = "books")
//
//
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = DarkGrayMe
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//
//                if (showLoadingPage) {
//                    LoadingPage(bookViewModel = bookViewModel, onTimeout = { showLoadingPage = false })
//                } else {
//                    LazyBooks(bookViewModel)
//                }
//
//            }
//        }
//    }