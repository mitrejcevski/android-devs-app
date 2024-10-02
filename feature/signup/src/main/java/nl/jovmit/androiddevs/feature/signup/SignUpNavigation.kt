package nl.jovmit.androiddevs.feature.signup

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
private data object SignUpRoute

fun NavGraphBuilder.signUpScreen(
    onNavigateUp: () -> Unit,
) {
    composable<SignUpRoute>(
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
        SignUpScreen(
            onNavigateUp = onNavigateUp,
        )
    }
}

fun NavController.navigateToSignUp() {
    navigate(SignUpRoute)
}