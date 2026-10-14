package com.example.a77formsapp.navigation

import android.net.Uri
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a77formsapp.data.remote.HrfConfig
import com.example.a77formsapp.data.remote.SessionEvents
import com.example.a77formsapp.data.remote.SessionStore
import com.example.a77formsapp.ui.home.HomeScreen
import com.example.a77formsapp.ui.login.LoginScreen
import com.example.a77formsapp.ui.theme.ThemeMode
import com.example.a77formsapp.ui.webview.FormWebViewScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val FORM = "form/{title}/{url}"

    fun form(title: String, url: String) = "form/${Uri.encode(title)}/${Uri.encode(url)}"
}

// Clears the whole back stack and shows the sign-in screen.
private fun NavHostController.goToLogin() {
    navigate(Routes.LOGIN) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

@Composable
fun AppNavigation(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    val navController = rememberNavController()

    // Start on the list if a session cookie already exists, otherwise on sign-in.
    val startDestination = remember { if (SessionStore.hasSession()) Routes.HOME else Routes.LOGIN }

    // If the server says the session expired, go back to sign-in.
    LaunchedEffect(Unit) {
        SessionEvents.expired.collect { navController.goToLogin() }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(initialOffsetX = { it / 4 }) + fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it / 4 }) + fadeOut() }
    ) {

        // Sign-in
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoggedIn = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Screen 1: Home View (Form List, loaded from API)
        composable(Routes.HOME) {
            HomeScreen(
                onFormClick = { form ->
                    navController.navigate(
                        Routes.form(form.title, HrfConfig.formUrl(form.hrFormCode))
                    )
                },
                themeMode = themeMode,
                onThemeModeChange = onThemeModeChange,
                onSignOut = {
                    SessionStore.clear()
                    navController.goToLogin()
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