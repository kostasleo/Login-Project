package com.example.loginproject.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginproject.api.ApiConstants.TOKEN
import com.example.loginproject.api.ApiService
import kotlinx.coroutines.launch
import retrofit2.http.Url
import java.net.URL
import java.util.Date

enum class BookState {
    DEFAULT, DOWNLOADING, DOWNLOADED
}

data class Book(
    var id: Int,
    var title: String,
    var image_url: String,
    var date_released: Date,
    var pdf_url: String,

    var state: BookState = BookState.DEFAULT,
    var pdfId: Int
)

class BooksViewModel: ViewModel(){
    private val _booksList = mutableStateListOf<Book>()
    var errorMessage: String by mutableStateOf("")
    val booksList: List<Book>?
        get() = _booksList
    var isLoadingBooks by mutableStateOf(false)

    fun getBooksList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                isLoadingBooks = true
                _booksList.clear()
                _booksList.addAll(apiService.getBooks(token = "Bearer $TOKEN"))
                isLoadingBooks = false
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getBooksByYear(booksList: List<Book>?, year: String) : List<Book> {
        val result = ArrayList<Book>()
        booksList?.forEach{ book ->
            if(year in book.date_released.toString()){
                result.add(book)
            }
        }
        return result
    }

    fun changeBookState(book: Book, state: BookState): BookState{
        book.state = state
        return book.state
    }
}