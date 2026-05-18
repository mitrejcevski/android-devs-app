package nl.jovmit.androiddevs.feature.timeline

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object TimelineRoute : NavKey

fun EntryProviderScope<NavKey>.timelineEntry(
    onItemClicked: (itemId: String) -> Unit,
    onAddPost: () -> Unit
) {
    entry<TimelineRoute> {
        TimelineScreen(
            onItemClicked = onItemClicked,
            onAddPost = onAddPost
        )
    }
}
