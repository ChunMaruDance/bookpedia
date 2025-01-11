package com.plcoding.bookpedia.core.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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

            composable<Route.BookDetail>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(1000)
                    ) + fadeIn()
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(1000)
                    ) + fadeOut()
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(1000)
                    ) + fadeOut()
                }
            ) { entry ->
                onBookDetail(entry)
            }

        }


    }

}

