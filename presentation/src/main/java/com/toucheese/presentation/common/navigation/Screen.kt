package com.toucheese.presentation.common.navigation

sealed class Screen(val route: String) {
    // Home
    data object Home: Screen("Home")
    // Sign In
    data object SignIn: Screen("SignIn")
    // Studio List
    data object StudioFilter: Screen("StudioFilter/{conceptId}")
    // Additional info
    data object AdditionalInfo: Screen("AdditionalInfo")
    // Sign Up
    data object SignUp: Screen("SignUp")
    // Sign Up Additional Info
    data object SignUpAdditionalInfo: Screen("SignUpAdditionalInfo/{email}/{password}")
}