package nl.jovmit.androiddevs.feature.timeline

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private const val TIMELINE_ROUTE = "timeline"

fun NavGraphBuilder.timelineScreen(
    onItemClicked: (itemId: String) -> Unit
) {
    composable(TIMELINE_ROUTE) {
        TimelineScreen(
            onItemClicked = onItemClicked
        )
    }
}

fun NavController.navigateToTimeline() {
    navigate(TIMELINE_ROUTE) {
        popUpTo(0)
    }
}