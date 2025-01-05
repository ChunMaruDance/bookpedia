package com.plcoding.bookpedia.book.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plcoding.bookpedia.book.presentation.SelectedBookViewModel
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UiNavigation() {


    val navController = rememberNavController()

    BookNavHost(
        navHostController = navController,
        onBookListCall = { navBackStack ->
            val viewModel = koinViewModel<BookListViewModel>()
            val selectedBookViewModel =
                navBackStack.sharedKoinViewModel<SelectedBookViewModel>(navController)

            LaunchedEffect(true) {
                selectedBookViewModel.onSelectBook(null)
            }

            BookListScreenRoot(
                viewModel = viewModel,
                onBookClick = { book ->
                    selectedBookViewModel.onSelectBook(book)
                    navController.navigate(Route.BookDetail(book.id))
                },
            )
        },
        onBookDetail = { entry ->

            val selectedBookViewModel =
                entry.sharedKoinViewModel<SelectedBookViewModel>(navController)

            val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

            Text(text = selectedBook.toString())


        }

    )


}


@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {

    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}

