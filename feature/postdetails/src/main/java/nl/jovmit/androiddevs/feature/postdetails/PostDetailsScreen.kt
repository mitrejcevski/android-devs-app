package nl.jovmit.androiddevs.feature.postdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.timeline.data.Comment
import nl.jovmit.androiddevs.domain.timeline.data.Post
import nl.jovmit.androiddevs.domain.timeline.data.ReactionType
import nl.jovmit.androiddevs.shared.ui.datetime.AppDateTime
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
internal fun PostDetailsScreenContainer(
  postId: String,
  viewModel: PostDetailsViewModel = hiltViewModel(),
  onNavigateUp: () -> Unit,
  onPostRemoved: () -> Unit
) {
  val screenState by viewModel.screenState.collectAsStateWithLifecycle()

  LaunchedEffect(postId) {
    viewModel.loadPostDetails(postId)
  }
  LaunchedEffect(screenState.isRemoved) {
    if (screenState.isRemoved) onPostRemoved()
  }

  PostDetailsScreen(
    screenState = screenState,
    onNavigateUp = onNavigateUp,
    onRemovePost = viewModel::removePost
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailsScreen(
  modifier: Modifier = Modifier,
  screenState: PostDetailsScreenState,
  onNavigateUp: () -> Unit,
  onRemovePost: () -> Unit
) {
  Scaffold(
    modifier = modifier,
    containerColor = AppTheme.colorScheme.background,
    topBar = {
      CenterAlignedTopAppBar(
        navigationIcon = {
          IconButton(onClick = onNavigateUp) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Navigate up",
              tint = AppTheme.colorScheme.onBackground
            )
          }
        },
        title = {
          Text(
            text = "Post Details",
            color = AppTheme.colorScheme.onBackground,
            style = AppTheme.typography.titleNormal
          )
        },
        actions = {
          if (screenState.postItem?.canRemove == true) {
            IconButton(onClick = onRemovePost) {
              Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete post",
                tint = AppTheme.colorScheme.error
              )
            }
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = AppTheme.colorScheme.background,
        )
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
      when {
        screenState.isLoading -> CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center),
          color = AppTheme.colorScheme.secondary
        )

        screenState.isNotFound -> DetailsMessage("This post is no longer available.")
        screenState.isOffline -> DetailsMessage("You are offline. This post cannot be reached right now.")
        screenState.isUnavailable -> DetailsMessage("Post details are unavailable. Please try again later.")
        screenState.postItem != null -> PostContent(screenState.postItem)
      }
    }
  }
}

@Composable
private fun PostContent(
  postItem: PostDetailsItem
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(AppTheme.size.medium),
    verticalArrangement = Arrangement.spacedBy(AppTheme.size.medium)
  ) {
    val formatter = AppDateTime.formatter
    Text(
      text = postItem.post.author.email,
      style = AppTheme.typography.labelNormal,
      color = AppTheme.colorScheme.onBackground
    )
    Text(
      text = formatter.toDateTime(postItem.post.createdAtMillis),
      style = AppTheme.typography.labelSmall,
      color = AppTheme.colorScheme.separator
    )
    Text(
      text = postItem.post.title,
      style = AppTheme.typography.titleLarge,
      color = AppTheme.colorScheme.onBackground
    )
    Text(
      text = postItem.post.body,
      style = AppTheme.typography.paragraph,
      color = AppTheme.colorScheme.onBackground
    )
    postItem.post.imageUrls.forEach { imageUrl ->
      PostImage(imageUrl)
    }
    ReactionSummary(postItem.post.reactions.groupingBy { it.type }.eachCount())
    CommentList(postItem.post.comments.map { it.toCommentItem() })
  }
}

@Composable
private fun ReactionSummary(reactions: Map<ReactionType, Int>) {
  Column(verticalArrangement = Arrangement.spacedBy(AppTheme.size.small)) {
    Text(
      text = "${reactions.values.sum()} reactions",
      style = AppTheme.typography.labelLarge,
      color = AppTheme.colorScheme.onBackground
    )
    reactions.entries.sortedBy { it.key.name }.forEach { (type, count) ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = type.label(),
          style = AppTheme.typography.labelNormal,
          color = AppTheme.colorScheme.onBackground
        )
        Text(
          text = count.toString(),
          style = AppTheme.typography.labelNormal,
          color = AppTheme.colorScheme.onBackground
        )
      }
    }
  }
}

@Composable
private fun CommentList(
  comments: List<CommentItem>
) {
  Column(verticalArrangement = Arrangement.spacedBy(AppTheme.size.small)) {
    Text(
      text = "${comments.size} comments",
      style = AppTheme.typography.labelLarge,
      color = AppTheme.colorScheme.onBackground
    )
    comments.forEach { comment ->
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(AppTheme.shape.container)
          .background(AppTheme.colorScheme.primary.copy(alpha = .12f))
          .padding(AppTheme.size.normal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.size.small)
      ) {
        Text(
          text = comment.author,
          style = AppTheme.typography.labelSmall,
          color = AppTheme.colorScheme.onBackground
        )
        Text(
          text = comment.body,
          style = AppTheme.typography.paragraph,
          color = AppTheme.colorScheme.onBackground
        )
      }
    }
  }
}

@Composable
private fun PostImage(imageUrl: String) {
  SubcomposeAsyncImage(
    modifier = Modifier
      .fillMaxWidth()
      .height(220.dp)
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
      .heightIn(min = 220.dp)
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
private fun DetailsMessage(message: String) {
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

private fun ReactionType.label(): String {
  return name.lowercase().replaceFirstChar { it.uppercase() }
}

@Composable
@PreviewLightDark
private fun PostDetailsScreenPreview() {
  AppTheme {
    PostDetailsScreen(
      screenState = PostDetailsScreenState(
        postItem = PostDetailsItem(
          post = Post(
            id = "post",
            author = User("uid", "maya@androiddevs.nl", ""),
            title = "Welcome Android Devs",
            body = "A full post body shows here.",
            createdAtMillis = System.currentTimeMillis(),
            imageUrls = emptyList(),
            comments = listOf(
              Comment(
                "cid",
                User("uid", "sam@androiddevs.nl", ""),
                "Great point.",
                System.currentTimeMillis()
              )
            ),
            reactions = emptyList(),
          ),
          canRemove = true
        )
      ),
      onNavigateUp = {},
      onRemovePost = {}
    )
  }
}
