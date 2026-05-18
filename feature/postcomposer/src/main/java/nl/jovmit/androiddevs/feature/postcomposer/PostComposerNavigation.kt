package nl.jovmit.androiddevs.feature.postcomposer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val POST_COMPOSER_ROUTE = "postComposer"

fun NavGraphBuilder.postComposerScreen(
    onNavigateUp: () -> Unit,
    onPostAdded: () -> Unit
) {
    composable(POST_COMPOSER_ROUTE) {
        PostComposerScreen(
            onNavigateUp = onNavigateUp,
            onPostAdded = onPostAdded
        )
    }
}

fun NavController.navigateToPostComposer() {
    navigate(POST_COMPOSER_ROUTE)
}
