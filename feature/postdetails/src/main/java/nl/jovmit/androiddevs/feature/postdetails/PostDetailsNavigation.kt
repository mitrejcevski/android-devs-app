package nl.jovmit.androiddevs.feature.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val POST_ID_ARG = "postId"
private const val POST_DETAILS_ROUTE = "postdetails/$POST_ID_ARG={$POST_ID_ARG}"

fun NavGraphBuilder.postDetailsScreen(
    onNavigateUp: () -> Unit
) {
    composable(POST_DETAILS_ROUTE) {
        PostDetailsScreenContainer(onNavigateUp = onNavigateUp)
    }
}

fun NavController.navigateToPostDetails(
    postId: String
) {
    navigate("postdetails/$POST_ID_ARG=$postId")
}

internal class PostDetailsArgs(val postId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(requireNotNull(savedStateHandle.get<String>(POST_ID_ARG)))
}