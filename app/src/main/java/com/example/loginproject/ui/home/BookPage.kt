package com.example.loginproject.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.loginproject.viewmodel.Book
import com.example.loginproject.viewmodel.BooksViewModel
import com.example.loginproject.R
import com.example.loginproject.viewmodel.BookState
import kotlinx.coroutines.delay

@Composable
fun BooksPage(navController: NavController, bookViewModel: BooksViewModel) {

    var showLoadingPage by remember { mutableStateOf(true)}

    Column(modifier = Modifier.fillMaxSize()) {

        TopLabel(type = "books")

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkGrayMe
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                if (showLoadingPage) {
                    LoadingPage(bookViewModel = bookViewModel, onTimeout = { showLoadingPage = false })
                } else {
                    LazyBooks(bookViewModel)
                }
            }
        }
    }
}

@Composable
fun LoadingPage(bookViewModel: BooksViewModel, onTimeout: () -> Unit){

    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(Unit, block = {
        bookViewModel.getBooksList()
        currentOnTimeout()
    })

    Text(
        text = "Loading",
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(50.dp)
    )
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
            if (bookState == BookState.DEFAULT) {
                IconButton(
                    onClick = {
                        bookState = bookViewModel.changeBookState(book,BookState.DOWNLOADING) },
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
        }

        if (book.state == BookState.DOWNLOADING) {
            Image(painter = painterResource(R.drawable.downloading2),
                contentDescription = null,
                modifier = Modifier
                    .height(14.dp)
                    .width(140.dp)
            )
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