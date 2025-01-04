package com.plcoding.bookpedia.book.presentation.book_list

sealed interface BookListAction {

    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val id: Int) : BookListAction
    data class OnTabSelected(val index: Int) : BookListAction

}