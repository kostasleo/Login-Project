package com.example.loginproject.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginproject.R
import com.example.loginproject.api.ApiConstants.TOKEN
import com.example.loginproject.api.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.http.Url
import java.net.URL
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
    var errorMessage: String by mutableStateOf("")
    val booksList: List<Book>?
        get() = _booksList
    var isLoadingBooks by mutableStateOf(false)
    var firstLoadingBooks by mutableStateOf(false)

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

    fun getBook(id: Int) : Book{
        return _booksList[id]
    }

    fun setBookPdfs() {
//        val books = booksViewModel.booksList
//        if (books != null) {

            for (book in _booksList!!) {
                val num = (0..9).random()
//                booksViewModel.setBookPdfId(book, num)
                book.pdfId = num
                val urls = listOf(
                    R.string.pdf_image_1,
                    R.string.pdf_image_2,
                    R.string.pdf_image_3,
                    R.string.pdf_image_4,
                    R.string.pdf_image_5,
                    R.string.pdf_image_6,
                    R.string.pdf_image_7,
                    R.string.pdf_image_8,
                    R.string.pdf_image_9,
                    R.string.pdf_image_10,
                )
//                booksViewModel.setBookImageUrl(book, urls[num].toString())
                book.image_url = urls[num].toString()
                book.state = BookState.DEFAULT
                Log.d("pdfs", "loaded pdfs")
            }
        }

    fun deletePdfs() {
        for (book in _booksList!!) {
            book.state = BookState.DEFAULT
        }
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
        Log.d("image_url", "${book.image_url}")
    }

    fun setBookChanged(book: Book) {
        book.changed = true
        Log.d("changed", "changed book ${book.id}")
    }
}