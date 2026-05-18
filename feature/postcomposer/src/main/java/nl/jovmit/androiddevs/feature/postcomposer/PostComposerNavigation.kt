package nl.jovmit.androiddevs.feature.postcomposer

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object PostComposerRoute : NavKey

fun EntryProviderScope<NavKey>.postComposerEntry(
    onNavigateUp: () -> Unit,
    onPostAdded: () -> Unit
) {
    entry<PostComposerRoute> {
        PostComposerScreen(
            onNavigateUp = onNavigateUp,
            onPostAdded = onPostAdded
        )
    }
}
