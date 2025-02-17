package com.toucheese.presentation.utils

sealed class Screen(val route: String) {
    // Home
    data object Home: Screen("Home")
}