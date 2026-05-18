package nl.jovmit.androiddevs.feature.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import kotlinx.collections.immutable.persistentListOf
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.timeline.data.Post
import nl.jovmit.androiddevs.domain.timeline.data.ReactionType
import nl.jovmit.androiddevs.shared.ui.composables.PrimaryButton
import nl.jovmit.androiddevs.shared.ui.datetime.AppDateTime
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
internal fun TimelineScreen(
  viewModel: TimelineViewModel = hiltViewModel(),
  onItemClicked: (itemId: String) -> Unit,
  onAddPost: () -> Unit
) {
  val screenState by viewModel.screenState.collectAsStateWithLifecycle()

  TimelineScreenContent(
    screenState = screenState,
    onItemClicked = onItemClicked,
    onAddPost = onAddPost,
    onRemovePost = viewModel::removePost,
    onForceLogOut = viewModel::doLogout
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimelineScreenContent(
  screenState: TimelineScreenState,
  onItemClicked: (itemId: String) -> Unit,
  onAddPost: () -> Unit,
  onRemovePost: (postId: String) -> Unit,
  onForceLogOut: () -> Unit
) {
  var postReactions by remember { mutableStateOf<TimelinePostItem?>(null) }

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
        actions = {
          IconButton(onClick = onForceLogOut) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.Logout,
              contentDescription = "Log out",
              tint = AppTheme.colorScheme.onBackground
            )
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = AppTheme.colorScheme.background,
        )
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = onAddPost,
        containerColor = AppTheme.colorScheme.primary,
        contentColor = AppTheme.colorScheme.onPrimary
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add post"
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when {
        screenState.isLoading -> CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center),
          color = AppTheme.colorScheme.secondary
        )

        screenState.isOffline -> TimelineMessage("You are offline. Posts cannot be reached right now.")
        screenState.isUnavailable -> TimelineMessage("Timeline is unavailable. Please try again later.")
        screenState.posts.isEmpty() -> EmptyTimeline(onAddPost = onAddPost)
        else -> LazyColumn(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.spacedBy(AppTheme.size.normal),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(AppTheme.size.medium)
        ) {
          items(screenState.posts, key = { it.id }) { timelinePost ->
            TimelinePostCard(
              timelinePostItem = timelinePost,
              onClick = { onItemClicked(timelinePost.id) },
              onReactionClick = { postReactions = timelinePost },
              onRemovePost = { onRemovePost(timelinePost.id) }
            )
          }
        }
      }
    }
  }

  postReactions?.let { post ->
    ModalBottomSheet(onDismissRequest = { postReactions = null }) {
      ReactionSummary(post)
    }
  }
}

@Composable
private fun TimelinePostCard(
  timelinePostItem: TimelinePostItem,
  onClick: () -> Unit,
  onReactionClick: () -> Unit,
  onRemovePost: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick),
    shape = AppTheme.shape.container,
    colors = CardDefaults.cardColors(
      containerColor = AppTheme.colorScheme.primary.copy(alpha = .14f)
    )
  ) {
    Column(
      modifier = Modifier.padding(AppTheme.size.medium),
      verticalArrangement = Arrangement.spacedBy(AppTheme.size.small)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          val formatter = AppDateTime.formatter
          Text(
            text = timelinePostItem.author,
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colorScheme.onBackground
          )
          Text(
            text = formatter.toDateTime(timelinePostItem.postItem.createdAtMillis),
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colorScheme.separator
          )
        }
        if (timelinePostItem.canRemove) {
          IconButton(onClick = onRemovePost) {
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = "Delete post",
              tint = AppTheme.colorScheme.error
            )
          }
        }
      }
      Text(
        text = timelinePostItem.title,
        style = AppTheme.typography.titleNormal,
        color = AppTheme.colorScheme.onBackground
      )
      Text(
        text = timelinePostItem.body,
        style = AppTheme.typography.paragraph,
        color = AppTheme.colorScheme.onBackground,
        maxLines = 3
      )
      PostImage(timelinePostItem.imageUrl)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.size.normal),
        verticalAlignment = Alignment.CenterVertically
      ) {
        TextButton(
          enabled = timelinePostItem.reactionCount > 0,
          onClick = onReactionClick
        ) {
          Text(
            text = "${timelinePostItem.reactionCount} reactions",
            style = AppTheme.typography.labelNormal,
            color = AppTheme.colorScheme.onBackground
          )
        }
        Text(
          text = "${timelinePostItem.commentCount} comments",
          style = AppTheme.typography.labelNormal,
          color = AppTheme.colorScheme.onBackground
        )
      }
    }
  }
}

@Composable
private fun PostImage(imageUrl: String?) {
  if (imageUrl == null) return

  SubcomposeAsyncImage(
    modifier = Modifier
      .fillMaxWidth()
      .height(160.dp)
      .clip(AppTheme.shape.container),
    model = imageUrl,
    contentDescription = null,
    contentScale = ContentScale.Crop,
    loading = { ImagePlaceholder() },
    error = { ImagePlaceholder() }
  )
}

@Composable
private fun ImagePlaceholder() {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(min = 160.dp)
      .background(AppTheme.colorScheme.separator.copy(alpha = .3f)),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Image unavailable",
      style = AppTheme.typography.labelNormal,
      color = AppTheme.colorScheme.onBackground
    )
  }
}

@Composable
private fun EmptyTimeline(onAddPost: () -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(AppTheme.size.large),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "No posts yet",
      style = AppTheme.typography.titleNormal,
      color = AppTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.size(AppTheme.size.normal))
    PrimaryButton(
      label = "Add Post",
      onClick = onAddPost
    )
  }
}

@Composable
private fun TimelineMessage(message: String) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(AppTheme.size.large),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = message,
      style = AppTheme.typography.paragraph,
      color = AppTheme.colorScheme.onBackground
    )
  }
}

@Composable
private fun ReactionSummary(
  timelinePost: TimelinePostItem
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(AppTheme.size.large),
    verticalArrangement = Arrangement.spacedBy(AppTheme.size.normal)
  ) {
    Text(
      text = "${timelinePost.reactionCount} reactions",
      style = AppTheme.typography.titleNormal,
      color = AppTheme.colorScheme.onBackground
    )
    timelinePost.reactionSummary.entries
      .sortedBy { it.key.name }
      .forEach { (type, count) ->
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = type.label(),
            style = AppTheme.typography.paragraph,
            color = AppTheme.colorScheme.onBackground
          )
          Text(
            text = count.toString(),
            style = AppTheme.typography.labelLarge,
            color = AppTheme.colorScheme.onBackground
          )
        }
      }
  }
}

private fun ReactionType.label(): String {
  return name.lowercase().replaceFirstChar { it.uppercase() }
}

@PreviewLightDark
@Composable
private fun PreviewTimelineScreen() {
  AppTheme {
    TimelineScreenContent(
      screenState = TimelineScreenState(
        posts = persistentListOf(
          TimelinePostItem(
            postItem = Post(
              id = "1",
              author = User("uid", "maya@androiddevs.nl", ""),
              title = "How I debug recomposition spikes",
              body = "I stopped guessing and started adding counters around expensive composables.",
              createdAtMillis = System.currentTimeMillis(),
              imageUrls = emptyList(),
              comments = persistentListOf(),
              reactions = persistentListOf(),
            ),
            canRemove = true
          )
        )
      ),
      onItemClicked = {},
      onAddPost = {},
      onRemovePost = {},
      onForceLogOut = {}
    )
  }
}
