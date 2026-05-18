package nl.jovmit.androiddevs.feature.postdetails

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class PostDetailsRoute(
    val postId: String
) : NavKey

fun EntryProviderScope<NavKey>.postDetailsEntry(
    onNavigateUp: () -> Unit,
    onPostRemoved: () -> Unit
) {
    entry<PostDetailsRoute> { route ->
        PostDetailsScreenContainer(
            postId = route.postId,
            onNavigateUp = onNavigateUp,
            onPostRemoved = onPostRemoved
        )
    }
}
