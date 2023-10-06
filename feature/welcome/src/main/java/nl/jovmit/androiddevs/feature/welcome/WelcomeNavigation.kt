package nl.jovmit.androiddevs.feature.welcome

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val WELCOME_ROUTE = "welcome"

fun NavGraphBuilder.welcomeScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    composable(WELCOME_ROUTE) {
        WelcomeScreen(
            onLogin = onLogin,
            onSignUp = onSignUp
        )
    }
}

