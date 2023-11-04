package nl.jovmit.androiddevs.feature.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.core.view.theme.PreviewLightDark

@Composable
internal fun TimelineScreen(
    viewModel: TimelineViewModel = hiltViewModel(),
    onItemClicked: (itemId: String) -> Unit
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val timelinePosts = viewModel.timelinePosts.collectAsLazyPagingItems()

    TimelineScreenContent(
        state = screenState,
        posts = timelinePosts,
        onLimitToggled = {
            viewModel.toggleLimit(it)
            timelinePosts.refresh()
        },
        onItemClicked = onItemClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimelineScreenContent(
    state: TimelineScreenState,
    posts: LazyPagingItems<ListItem>,
    onLimitToggled: (isToggled: Boolean) -> Unit,
    onItemClicked: (itemId: String) -> Unit,
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
                ),
                actions = {
                    Switch(
                        checked = state.showLimitedContent,
                        onCheckedChange = onLimitToggled
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(
                count = posts.itemCount,
                key = posts.itemKey { it.id },
            ) { index ->
                posts[index]?.let { postItem ->
                    TimelinePostItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClicked(postItem.id) }
                            .padding(AppTheme.size.medium),
                        post = postItem,
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelinePostItem(
    modifier: Modifier = Modifier,
    post: ListItem,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = post.title,
            style = AppTheme.typography.labelLarge,
            color = AppTheme.colorScheme.onPrimary
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewTimelineScreen() {
    AppTheme {
        val pagingData = PagingData.from(
            (1..10).map { ListItem("$it", "Item $it") }
        )
        TimelineScreenContent(
            state = TimelineScreenState(),
            posts = flowOf(pagingData).collectAsLazyPagingItems(),
            onLimitToggled = {},
            onItemClicked = {}
        )
    }
}