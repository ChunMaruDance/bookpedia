package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> TODO()
            BookDetailAction.OnFavoriteClick -> TODO()
            is BookDetailAction.OnSelectedBookChange -> TODO()
        }
    }


}