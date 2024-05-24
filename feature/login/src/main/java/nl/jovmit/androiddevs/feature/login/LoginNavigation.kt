package nl.jovmit.androiddevs.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val LOGIN_ROUTE = "login"

fun NavGraphBuilder.loginScreen(
    onLoggedIn: (user: User) -> Unit,
    onNavigateUp: () -> Unit
) {
    composable(LOGIN_ROUTE) {
        LoginScreen(
            onLoggedIn = onLoggedIn,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToLogin() {
    navigate(LOGIN_ROUTE)
}