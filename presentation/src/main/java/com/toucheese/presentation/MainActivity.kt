package com.toucheese.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.toucheese.presentation.navigation.ToucheeseNavigation
import com.toucheese.presentation.ui.theme.ToucheeseCleanTheme
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