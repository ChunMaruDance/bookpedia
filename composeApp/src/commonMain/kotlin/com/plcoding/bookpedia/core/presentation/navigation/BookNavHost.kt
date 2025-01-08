package com.plcoding.bookpedia.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun BookNavHost(
    navHostController: NavHostController,
    onBookListCall: @Composable (NavBackStackEntry) -> Unit,
    onBookDetail: @Composable (NavBackStackEntry) -> Unit
) {

    NavHost(navController = navHostController, startDestination = Route.BookGraph) {

        navigation<Route.BookGraph>(
            startDestination = Route.BookList
        ) {
            composable<Route.BookList> { onBookListCall(it) }

            composable<Route.BookDetail> { entry ->
                onBookDetail(entry)
            }

        }


    }

}

