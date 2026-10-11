package com.example.a77formsapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a77formsapp.ui.home.HomeScreen
import com.example.a77formsapp.ui.webview.FormWebViewScreen

object Routes {
    const val HOME = "home"
    const val FORM = "form/{title}/{url}"

    fun form(title: String, url: String) = "form/${Uri.encode(title)}/${Uri.encode(url)}"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        // Screen 1: Home View (Form List, loaded from API)
        composable(Routes.HOME) {
            HomeScreen(
                onFormClick = { form ->
                    navController.navigate(Routes.form(form.title, form.url))
                }
            )
        }

        // Screen 2: Form Web View Container (shows the form URL)
        composable(
            route = Routes.FORM,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("url") { type = NavType.StringType }
            )
        ) {
            FormWebViewScreen(onBack = { navController.popBackStack() })
        }
    }
}