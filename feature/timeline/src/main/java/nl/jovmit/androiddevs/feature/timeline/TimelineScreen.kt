package nl.jovmit.androiddevs.feature.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.core.view.theme.PreviewLightDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimelineScreen(
    onItemClicked: (itemId: String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Timeline",
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.titleNormal
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.colorScheme.background,
                )
            )
        }
    ) { paddingValues ->
        val items = (1..50).map { ListItem(it.toString(), "Item $it") }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClicked(item.id)
                        }
                        .padding(AppTheme.size.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        style = AppTheme.typography.labelLarge,
                        color = AppTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

private data class ListItem(
    val id: String,
    val title: String
)

@PreviewLightDark
@Composable
private fun PreviewTimelineScreen() {
    AppTheme {
        TimelineScreen(
            onItemClicked = {}
        )
    }
}