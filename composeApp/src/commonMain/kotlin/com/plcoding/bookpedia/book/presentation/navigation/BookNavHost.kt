package com.plcoding.bookpedia.book.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute

@Composable
fun BookNavHost(
    navHostController: NavHostController,
    onBookListCall: @Composable () -> Unit,
    onBookDetail: @Composable (String) -> Unit
) {

    NavHost(navController = navHostController, startDestination = Route.BookGraph) {

        navigation<Route.BookGraph>(
            startDestination = Route.BookList
        ) {
            composable<Route.BookList> { onBookListCall() }

            composable<Route.BookDetail> { entry ->
                val args = entry.toRoute<Route.BookDetail>()
                onBookDetail(args.id)
            }

        }


    }

}
