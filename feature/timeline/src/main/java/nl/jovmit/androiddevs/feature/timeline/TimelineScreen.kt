package nl.jovmit.androiddevs.feature.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jovmit.androiddevs.shared.ui.composables.PrimaryButton
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
internal fun TimelineScreen(
    viewModel: TimelineViewModel = hiltViewModel(),
    onItemClicked: (itemId: String) -> Unit,
) {

    TimelineScreenContent(
        onItemClicked = onItemClicked,
        onForceLogOut = viewModel::doLogout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimelineScreenContent(
    onItemClicked: (itemId: String) -> Unit,
    onForceLogOut: () -> Unit
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val items = (1..50).map { ListItem(it.toString(), "Item $it") }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
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
            PrimaryButton(
                label = "Log Me Out",
                onClick = onForceLogOut
            )
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
        TimelineScreenContent(
            onItemClicked = {},
            onForceLogOut = {}
        )
    }
}