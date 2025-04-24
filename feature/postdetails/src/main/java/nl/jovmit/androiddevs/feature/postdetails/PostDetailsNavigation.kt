package nl.jovmit.androiddevs.feature.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data class PostDetailsDestination(
    val postId: String
) {

    constructor(savedStateHandle: SavedStateHandle):
            this(requireNotNull(savedStateHandle.get<String>("postId")))

    companion object {
        fun from(savedStateHandle: SavedStateHandle): PostDetailsDestination {
            val postId = requireNotNull(savedStateHandle.get<String>("postId"))
            return PostDetailsDestination(postId)
        }
    }
}

fun NavGraphBuilder.postDetailsScreen(
    onNavigateUp: () -> Unit
) {
    composable<PostDetailsDestination> { backStackEntry ->
        PostDetailsScreenContainer(onNavigateUp = onNavigateUp)
    }
}

fun NavController.navigateToPostDetails(
    postId: String,
) {
    navigate(PostDetailsDestination(postId))
}