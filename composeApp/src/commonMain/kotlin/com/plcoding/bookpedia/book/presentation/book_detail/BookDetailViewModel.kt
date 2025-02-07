package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.book.domain.repositories.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.presentation.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchDescription()
            observeFavoriteState()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> Unit
            BookDetailAction.OnFavoriteClick -> {

                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        bookRepository.deleteFromFavorite(bookId)
                    } else {
                        state.value.book?.let { book ->
                            bookRepository.markAsFavorite(book)
                        }

                    }

                }
            }

            is BookDetailAction.OnSelectedBookChange -> {
                _state.update { it.copy(book = action.book) }
            }
        }
    }

    private fun observeFavoriteState() = viewModelScope.launch {
        bookRepository.isBookFavorite(bookId)
            .onEach { isFavorite ->
                _state.update { it.copy(isFavorite = isFavorite) }
            }
            .launchIn(viewModelScope)
    }


    private fun fetchDescription() = viewModelScope.launch {
        bookRepository
            .getBookDescription(bookId)
            .onSuccess { desc ->
                _state.update {
                    it.copy(
                        book = it.book?.copy(description = desc),
                        isLoading = false
                    )
                }
            }

    }


}