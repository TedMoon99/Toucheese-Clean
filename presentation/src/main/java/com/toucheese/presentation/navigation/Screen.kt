package com.toucheese.presentation.navigation

sealed class Screen(val route: String) {
    // Home
    data object Home: Screen("Home")
}