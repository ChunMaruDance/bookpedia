package com.plcoding.bookpedia


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.repositories.BookRepositoryImpl
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine

@Composable
fun App(engine:HttpClientEngine) {
    MaterialTheme {
        //todo navigation

        BookListScreenRoot(
            onBookClick = {},
            viewModel = remember { BookListViewModel(
                BookRepositoryImpl(KtorRemoteBookDataSource(
                    HttpClientFactory.create(engine)
                ))
            ) }
        )

    }
}