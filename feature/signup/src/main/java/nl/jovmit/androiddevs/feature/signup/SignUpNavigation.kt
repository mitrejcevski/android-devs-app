package nl.jovmit.androiddevs.feature.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
private data object SignUpRoute

fun NavGraphBuilder.signUpScreen(
    onNavigateUp: () -> Unit,
) {
    composable<SignUpRoute> {
        SignUpScreen(
            onNavigateUp = onNavigateUp,
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(SignUpRoute)
}