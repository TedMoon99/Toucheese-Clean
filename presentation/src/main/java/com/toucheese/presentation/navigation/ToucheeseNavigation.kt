package com.toucheese.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.toucheese.presentation.ui.screens.HomeScreen
import com.toucheese.presentation.ui.screens.SignInScreen
import com.toucheese.presentation.utils.Navigation.Companion.bottomNavClicked
import com.toucheese.presentation.utils.Screen

@Composable
fun ToucheeseNavigation() {
    val navController = rememberNavController()
    val hostState = remember { SnackbarHostState() }
    var bottomNavSelectedTab by remember { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {

        composable(Screen.Home.route){ backStackEntry: NavBackStackEntry ->
            HomeScreen(
                selectedTab = bottomNavSelectedTab,
                onTabSelected = { selectedTab ->
                    bottomNavClicked(
                        selectedTab = selectedTab,
                        navController = navController
                    )
                },
                onCardClick = { conceptId: Int ->
                    // 컨셉 스튜디오 조회 페이지로 이동

                }
            )
        }

        composable(Screen.SignIn.route){ backStackEntry: NavBackStackEntry ->
            SignInScreen(
                hostState = hostState,
                onSignInClicked = {

                },
                onKakaoSignInClicked = {

                }
            )
        }
    }
}