package com.example.sistemainfo.feature.commons.compose.navigation

import androidx.navigation.NavController
import com.example.sistemainfo.R

fun NavController.composeNavigate(
    route: String,
    popStack: Boolean = false,
    launchSingleTop: Boolean = true
) = navigate(route = route) {
    val startDestinationRoute = graph.startDestinationRoute.orEmpty()
    if (popStack && startDestinationRoute.isNotEmpty()) {
        popUpTo(startDestinationRoute) { inclusive = true }
    }
    this.launchSingleTop = launchSingleTop
    anim {
        enter = R.anim.fast_fade_in
        exit = R.anim.fast_fade_out
    }
}