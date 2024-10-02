package nl.jovmit.androiddevs.feature.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val LOGIN_ROUTE = "login"

fun NavGraphBuilder.loginScreen(
    onLoggedIn: () -> Unit,
    onNavigateUp: () -> Unit
) {
    composable(
        LOGIN_ROUTE,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                tween(easing = FastOutSlowInEasing)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                tween(easing = FastOutSlowInEasing)
            )
        }
    ) {
        LoginScreen(
            onLoggedIn = onLoggedIn,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToLogin() {
    navigate(LOGIN_ROUTE) {
        launchSingleTop = true
    }
}