package com.plcoding.bookpedia.book.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UiNavigation() {


    val navController = rememberNavController()

    BookNavHost(
        navHostController = navController,
        onBookListCall = {
            val viewModel = koinViewModel<BookListViewModel>()
            BookListScreenRoot(
                viewModel = viewModel,
                onBookClick = { book ->
                    navController.navigate(Route.BookDetail(book.id))
                },
            )
        },
        onBookDetail = { id ->
            Box {
                Text(id)
            }

        }

    )


}