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
//    val datesList = {items(booksList) { book -> book.} }

    var showLoadingPage by remember { mutableStateOf(true)}
//    var booksViewModel by remember { mutableStateOf(bookViewModel.booksList)}

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
//        delay(2_000L)
        currentOnTimeout()
        Log.e("apicall", "Called API")
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
//    val filteredList = bookViewModel.getBooksByYear(bookViewModel.booksList,"2020")

    val state = rememberLazyListState()

    Column(modifier = Modifier.padding(start = 38.dp, end = 18.dp)) {

        if (booksList != null) {
//            LazyColumn(state = state,
            LazyVerticalGrid(
                GridCells.Adaptive(140.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp),
                horizontalArrangement = Arrangement.spacedBy(26.dp),
                modifier = Modifier
                    .fillMaxHeight()
//                            .padding(38.dp)
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

//    bookViewModel.changeBookState(book, BookState.DEFAULT)
    var bookState by rememberSaveable {
        mutableStateOf(bookViewModel.changeBookState(book,BookState.DEFAULT))}


    val num = (0..9).random()
//    val url:String = stringResource(id = num)
    val urls = listOf<String>(
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
//                    .padding(top = 0.dp)
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

//
//        Spacer(modifier = Modifier.height(20.dp))
    }
}

//@Composable
//fun ShowBooks(bookViewModel: BooksViewModel, year: String) {
//    val filteredList = bookViewModel.getBooksByYear(bookViewModel.booksList, "2020")
//
//    if (bookViewModel.errorMessage.isEmpty()) {
//        LazyColumn() {
//            items(filteredList) { book ->
//                Column {
//                    if ("2019" in book.date_released.toString()) {
//                        Text(
//                            text = book.date_released.toString(),
////                                    text = "Book",
//                            modifier = Modifier.fillMaxWidth(),
//                            color = Color.White
//                        )
////                                Image(painter = book.image_url, contentDescription = )
//                        Spacer(modifier = Modifier.height(20.dp))
//                    }
//                }
//            }
//        }
//    }
//}


//@Composable
//fun TodoView(vm: TodoViewModel) {
//
//    val todoList = vm.todoList
//
//    LaunchedEffect(Unit, block = {
//        vm.getTodoList()
//    })
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Row {
//                        Text("Todos")
//                    }
//                })
//        },
//        content = {
//            if (vm.errorMessage.isEmpty()) {
//                Column(modifier = Modifier.padding(16.dp)) {
////                    if (todoList != null) {
//                        LazyColumn(modifier = Modifier.fillMaxHeight()) {
//                            items(vm.todoList!!) { todo ->
//                                Column {
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(16.dp),
//                                        horizontalArrangement = Arrangement.SpaceBetween
//                                    ) {
//                                        Box(
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .padding(0.dp, 0.dp, 16.dp, 0.dp)
//                                        ) {
//                                            Text(
//                                                todo.title,
//                                                maxLines = 1,
//                                                overflow = TextOverflow.Ellipsis
//                                            )
//                                        }
//                                        Spacer(modifier = Modifier.width(16.dp))
//                                        Checkbox(checked = todo.completed, onCheckedChange = null)
//                                    }
//                                    Divider()
//                                }
//                            }
////                        }
//                    }
//                }
//            } else {
//                Text(vm.errorMessage)
//            }
//        }
//    )
//}



//@Composable
//fun MainPage(viewModel: AuthenticationViewModel) {
//
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "login" ){
//        composable("login") {
//            if(viewModel.isLoggedTho.value){
//                navController.navigate("home")
//            } else {
//                LoginPage(viewModel = AuthenticationViewModel(), context = LocalContext.current)
//            }
//        }
//        composable("home") { HomePage() }
//    }
//}