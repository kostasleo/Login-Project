package com.example.loginproject.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.loginproject.R
import com.example.loginproject.viewmodel.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BooksPage(bookViewModel: BooksViewModel) {

    // just a scrollable list of books
    LazyBooks(bookViewModel = bookViewModel)
}

@OptIn(ExperimentalFoundationApi::class)    // needed for LazyVerticalGrid
@Composable
fun LazyBooks(bookViewModel: BooksViewModel) {

    val booksList = bookViewModel.booksList

    Column(modifier = Modifier.padding(start = 38.dp, end = 18.dp)) {

        // Lazy (scrollable) grid of books by year
        if (booksList != null) {
            LazyVerticalGrid(
                GridCells.Adaptive(140.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                // 2020
                item(span = { GridItemSpan(2) }) {
                    YearLabel(year = "2020")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList, "2020")) { book ->
                    Book(bookViewModel, book)
                }
                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(38.dp))
                }

                // 2019
                item(span = { GridItemSpan(2) }) {
                    YearLabel(year = "2019")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList, "2019")) { book ->
                    Book(bookViewModel, book)
                }

                // 2018
                item(span = { GridItemSpan(2) }) {
                    YearLabel(year = "2018")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList, "2018")) { book ->
                    Book(bookViewModel, book)
                }

                // 2010
                item(span = { GridItemSpan(2) }) {
                    YearLabel(year = "2010")
                }
                items(bookViewModel.getBooksByYear(bookViewModel.booksList, "2010")) { book ->
                    Book(bookViewModel, book)
                }
            }
        } else {
            Text(bookViewModel.errorMessage)
        }
    }
}

@Composable
fun YearLabel(year: String) {
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

    // handing frozen "download" of books on recomposition
    LaunchedEffect(Unit){
        if (book.state != BookState.DEFAULT){
            bookViewModel.changeBookState(book, BookState.DOWNLOADED)
        }
    }

    var bookState by rememberSaveable {
        mutableStateOf(bookViewModel.getBookState(book))
    }

    if (bookState == null) {
        bookState = BookState.DEFAULT
    }

    // getting a random number to pick one of available image urls
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

    // setting new image urls and to changed, so they remain as is
    if (!book.changed) {
        bookViewModel.setBookPdfId(book, num)
        bookViewModel.setBookImageUrl(book, urls[num])
        bookViewModel.setBookChanged(book)
        bookViewModel.changeBookState(book, BookState.DEFAULT)
    }

    // Book UI
    Column(
        modifier = Modifier
            .height(230.dp)
            .width(140.dp)
    )
    {
        Box(modifier = Modifier) {
            // book image
            Image(
                painter = rememberAsyncImagePainter(book.image_url),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(180.dp)
                    .width(140.dp)
            )
            val scope = rememberCoroutineScope()

            // handle default book state UI
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
            // handle downloading book state UI
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
            // handle downloaded book state UI
            if (book.state == BookState.DOWNLOADED) {
                Box(modifier = Modifier.padding(top = 130.dp, start = 91.dp)) {
                    Image(
                        painter = painterResource(R.drawable.downloaded_bg),
                        contentDescription = null,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                    Box(modifier = Modifier.padding(top = 26.dp, start = 24.dp)) {

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

        // book title, single line
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