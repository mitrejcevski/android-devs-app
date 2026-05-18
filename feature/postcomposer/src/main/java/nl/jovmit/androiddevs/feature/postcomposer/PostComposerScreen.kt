package nl.jovmit.androiddevs.feature.postcomposer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jovmit.androiddevs.shared.ui.composables.PrimaryButton
import nl.jovmit.androiddevs.shared.ui.composables.TextInput
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
internal fun PostComposerScreen(
    viewModel: PostComposerViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onPostAdded: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(screenState.isPosted) {
        if (screenState.isPosted) onPostAdded()
    }

    PostComposerScreenContent(
        screenState = screenState,
        onNavigateUp = onNavigateUp,
        onTitleChanged = viewModel::updateTitle,
        onBodyChanged = viewModel::updateBody,
        onAddPost = viewModel::addPost
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostComposerScreenContent(
    screenState: PostComposerScreenState,
    onNavigateUp: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onBodyChanged: (String) -> Unit,
    onAddPost: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                        text = "New Post",
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.titleNormal
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppTheme.size.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = screenState.title,
                label = "Title",
                hint = "What are you sharing?",
                onTextChanged = onTitleChanged,
                error = {
                    if (screenState.isTitleBlank) ErrorText("Title cannot be blank")
                }
            )
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = screenState.body,
                label = "Body",
                hint = "Tell Android devs what you learned",
                singleLine = false,
                onTextChanged = onBodyChanged,
                error = {
                    if (screenState.isBodyBlank) ErrorText("Body cannot be blank")
                }
            )
            ErrorState(screenState)
            if (screenState.isLoading) {
                CircularProgressIndicator(color = AppTheme.colorScheme.secondary)
            } else {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Publish",
                    onClick = onAddPost
                )
            }
        }
    }
}

@Composable
private fun ErrorState(screenState: PostComposerScreenState) {
    when {
        screenState.isOffline -> ErrorText("You are offline. The post cannot be published right now.")
        screenState.isUnavailable -> ErrorText("Timeline is unavailable. Please try again later.")
        screenState.isNotSignedIn -> ErrorText("Sign in again before publishing a post.")
    }
}

@Composable
private fun ErrorText(message: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = message,
        color = AppTheme.colorScheme.error,
        style = AppTheme.typography.labelNormal
    )
}

@PreviewLightDark
@Composable
private fun PreviewPostComposerScreen() {
    AppTheme {
        PostComposerScreenContent(
            screenState = PostComposerScreenState(),
            onNavigateUp = {},
            onTitleChanged = {},
            onBodyChanged = {},
            onAddPost = {}
        )
    }
}
