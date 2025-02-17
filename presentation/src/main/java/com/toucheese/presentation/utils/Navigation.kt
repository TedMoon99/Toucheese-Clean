package com.toucheese.presentation.utils

import androidx.navigation.NavController

class Navigation {

    companion object {
        fun bottomNavClicked(selectedTab: Int, navController: NavController) {
            when (selectedTab) {
                // 홈화면으로 이동
                0 -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }

                }
            }
        }
    }
}
