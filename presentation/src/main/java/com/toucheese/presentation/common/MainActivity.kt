package com.toucheese.presentation.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.toucheese.presentation.common.navigation.ToucheeseNavigation
import com.toucheese.presentation.common.theme.ToucheeseCleanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToucheeseCleanTheme {
                ToucheeseNavigation()

            }
        }
    }
}