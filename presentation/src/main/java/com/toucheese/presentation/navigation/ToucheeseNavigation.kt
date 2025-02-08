package com.toucheese.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.toucheese.presentation.ui.screens.HomeScreen

@Composable
fun ToucheeseNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route){ backStackEntry: NavBackStackEntry ->
            HomeScreen()
        }
    }
}