package com.example.a77formsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a77formsapp.navigation.AppNavigation
import com.example.a77formsapp.ui.theme.FormsTheme
import com.example.a77formsapp.ui.theme.ThemeMode
import com.example.a77formsapp.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val themeMode by themeViewModel.themeMode.collectAsState()

            val darkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            FormsTheme(darkTheme = darkTheme) {
                AppNavigation(
                    themeMode = themeMode,
                    onThemeModeChange = themeViewModel::setThemeMode
                )
            }
        }
    }
}