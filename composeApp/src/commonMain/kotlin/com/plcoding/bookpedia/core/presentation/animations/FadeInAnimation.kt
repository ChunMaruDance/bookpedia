package com.plcoding.bookpedia.core.presentation.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun FadeInAnimation(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val transitionState = remember { MutableTransitionState(false) }

    LaunchedEffect(true) {
        transitionState.targetState = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visibleState = transitionState,
        enter = fadeIn(animationSpec = tween(1500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        content()
    }



}