package nl.jovmit.androiddevs.feature.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val SIGN_UP_ROUTE = "signup"

fun NavGraphBuilder.signUpScreen(
    onNavigateUp: () -> Unit,
) {
    composable(SIGN_UP_ROUTE) {
        SignUpScreen(
            onNavigateUp = onNavigateUp,
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(SIGN_UP_ROUTE)
}