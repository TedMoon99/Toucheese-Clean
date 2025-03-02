package com.toucheese.presentation.utils

sealed class Screen(val route: String) {
    // Home
    data object Home: Screen("Home")
    // Sign In
    data object SignIn: Screen("SignIn")
    // Studio List
    data object StudioFilter: Screen("StudioFilter/{conceptId}")
}