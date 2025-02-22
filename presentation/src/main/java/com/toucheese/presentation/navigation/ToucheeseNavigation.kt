package com.toucheese.presentation.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tedmoon99.domain.usecase.member.SignInResult
import com.toucheese.presentation.ui.screens.HomeScreen
import com.toucheese.presentation.ui.screens.SignInScreen
import com.toucheese.presentation.utils.Navigation.Companion.bottomNavClicked
import com.toucheese.presentation.utils.Screen
import kotlinx.coroutines.launch

@Composable
fun ToucheeseNavigation() {
    val navController = rememberNavController()
    val hostState = remember { SnackbarHostState() }
    var bottomNavSelectedTab by remember { mutableIntStateOf(0) }
    val coroutine = rememberCoroutineScope()

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
                onSignInClicked = { signInResult ->
                    when (signInResult){
                        is SignInResult.Success -> {
                            navController.navigate(Screen.Home.route){
                                popUpTo(navController.graph.id)
                            }
                        }
                        is SignInResult.Failure -> {
                            coroutine.launch {
                                hostState.showSnackbar(
                                    message = "로그인 오류",
                                    duration =  SnackbarDuration.Short
                                )
                            }
                        }
                        is SignInResult.NetworkError -> {
                            coroutine.launch {
                                hostState.showSnackbar(
                                    message = "네트워크 오류",
                                    duration =  SnackbarDuration.Short
                                )
                            }
                        }
                    }

                },
                onKakaoSignInClicked = {

                }
            )
        }
    }
}