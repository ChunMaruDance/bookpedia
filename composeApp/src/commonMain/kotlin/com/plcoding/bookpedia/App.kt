package com.plcoding.bookpedia


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.plcoding.bookpedia.core.presentation.navigation.UiNavigation

@Composable
fun App() {
    MaterialTheme {
        UiNavigation()
    }
}