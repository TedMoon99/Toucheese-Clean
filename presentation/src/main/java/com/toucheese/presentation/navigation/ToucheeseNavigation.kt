package com.toucheese.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.UpdateInfoResult
import com.toucheese.presentation.ui.screens.AdditionalInfoScreen
import com.toucheese.presentation.ui.screens.HomeScreen
import com.toucheese.presentation.ui.screens.SignInScreen
import com.toucheese.presentation.ui.screens.SignUpScreen
import com.toucheese.presentation.ui.screens.StudioFilterScreen
import com.toucheese.presentation.ui.viewmodel.MemberViewModel
import com.toucheese.presentation.utils.Navigation.Companion.bottomNavClicked
import com.toucheese.presentation.utils.Screen
import kotlinx.coroutines.launch

@Composable
fun ToucheeseNavigation(
    viewModel: MemberViewModel = hiltViewModel()
) {
    val TAG = "ToucheeseNavigation"
    val navController = rememberNavController()
    val hostState = remember { SnackbarHostState() }
    var bottomNavSelectedTab by remember { mutableIntStateOf(0) }
    val coroutine = rememberCoroutineScope()
    // 로그인 상태 관리
    val isSignedOut by viewModel.signOutState.collectAsState()

    LaunchedEffect(isSignedOut) {
        when (isSignedOut){
            true -> {
                Log.d(TAG, "로그아웃 상태: $isSignedOut")
                // 로그인 화면으로 이동
                navController.navigate(Screen.SignIn.route){
                    popUpTo(0) { inclusive = true }
                }
            }
            else -> {
                // 필요 시 구현
                Log.d(TAG, "로그아웃 상태: $isSignedOut")
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isSignedOut) Screen.SignIn.route else Screen.Home.route
    ) {

        composable(Screen.Home.route) { backStackEntry: NavBackStackEntry ->

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
                    navController.navigate(
                        Screen.StudioFilter.route
                            .replace("{conceptId}", "$conceptId")
                    )
                }
            )
        }

        composable(Screen.SignIn.route) { backStackEntry: NavBackStackEntry ->
            SignInScreen(
                hostState = hostState,
                onSignInClicked = { signInResult ->
                    when (signInResult) {
                        is SignInResult.Success -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.id)
                            }
                        }

                        is SignInResult.Failure -> {
                            coroutine.launch {
                                hostState.showSnackbar(
                                    message = "로그인 오류",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                        is SignInResult.NetworkError -> {
                            coroutine.launch {
                                hostState.showSnackbar(
                                    message = "네트워크 오류",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }

                },
                onKakaoSignInClicked = { result ->
                    when (result.success){
                        true -> {
                            if (result.isFirstLogin){
                                // 추가 정보 입력화면으로 이동
                                navController.navigate(Screen.AdditionalInfo.route) {
                                    popUpTo(navController.graph.id)
                                }
                            }
                            else {
                                // Home 화면으로 이동
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(navController.graph.id)
                                }
                            }

                        }

                        else -> {
                            coroutine.launch {
                                hostState.showSnackbar(
                                    message = "카카오 로그인 실패",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            Log.e(TAG, "카카오 로그인 실패: ${result}")
                        }
                    }
                },
                onSignUpClicked = {
                    // 회원가입
                    navController.navigate(Screen.SignUp.route)
                },
                onFindIdClicked = {
                    // Id & PW 찾기
                }
            )
        }

        composable(
            route = Screen.StudioFilter.route,
            arguments = listOf(
                navArgument("conceptId") { type = NavType.IntType }
            )
        ) { navBackStackEntry: NavBackStackEntry ->

            val conceptId = navBackStackEntry.arguments?.getInt("conceptId") ?: -1

            StudioFilterScreen(
                conceptId = conceptId,
                modifier = Modifier.fillMaxSize(),
                onClickLeadingIcon = {
                    // 뒤로가기
                    navController.popBackStack()
                },
                onClickTrailingIcon = {
                    // 장바구니로 이동
                }

            )
        }

        composable(
            route = Screen.AdditionalInfo.route,
        ){ navBackStackEntry: NavBackStackEntry ->

            AdditionalInfoScreen(
                hostState = hostState,
                onNextClicked = { result: UpdateInfoResult ->
                    if (result.success){
                        // Home 화면으로 이동
                        navController.navigate(Screen.Home.route){
                            popUpTo(navController.graph.id){ inclusive = true }
                        }
                    } else {
                        Log.e(TAG, "추가 정보 업데이트 오류: ${result.errorMessage}")
                        coroutine.launch {
                            hostState.showSnackbar(
                                message = result.errorMessage!!,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            )

        }

        composable(Screen.SignUp.route){ navBackStackEntry: NavBackStackEntry ->
            SignUpScreen(
                onNextClicked = { email: String, password: String ->
                    navController.navigate(
                        Screen.SignUpAdditionalInfo.route
                            .replace("{email}", email)
                            .replace("{password}", password)
                    )
                },
                onLeadingIconClicked = {
                    navController.popBackStack() // 뒤로가기
                }
            )
        }

        composable(
            Screen.SignUpAdditionalInfo.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
            ),
        ) { navBackStackEntry: NavBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email") ?: ""
            val password = navBackStackEntry.arguments?.getString("password") ?: ""
            Log.d(TAG, "받아온 데이터\n" +
                    "email: $email\n" +
                    "password: $password"
            )

        }


    }
}