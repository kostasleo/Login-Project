package com.example.loginproject.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginproject.api.ApiConstants.TOKEN
import com.example.loginproject.api.ApiService
import kotlinx.coroutines.launch
import java.util.Date

enum class BookState {
    DEFAULT, DOWNLOADING1, DOWNLOADING2, DOWNLOADED
}

data class Book(
    var id: Int = 1,
    var title: String = "",
    var image_url: String = "",
    var date_released: Date,
    var pdf_url: String = "",
    var state: BookState = BookState.DEFAULT,
    var pdfId: Int,
    var changed: Boolean = false
)

class BooksViewModel: ViewModel(){
    private val _booksList = mutableStateListOf<Book>()
    val booksList: List<Book>?
        get() = _booksList
    var isLoadingBooks by mutableStateOf(false)
    var firstLoadingBooks by mutableStateOf(false)
    var errorMessage: String by mutableStateOf("")

    // making books request
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

    fun getBookState(book: Book): BookState{
        return book.state
    }

    fun changeBookState(book: Book, state: BookState): BookState{
        book.state = state
        return book.state
    }

    fun setBookPdfId(book: Book, image_id: Int) {
        book.pdfId = image_id
    }

    fun setBookImageUrl(book: Book, image_url: String) {
        book.image_url = image_url
    }

    fun setBookChanged(book: Book) {
        book.changed = true
    }

    fun deletePdfs() {
        for (book in _booksList) {
            book.state = BookState.DEFAULT
        }
    }
}