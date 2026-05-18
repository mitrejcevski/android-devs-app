package nl.jovmit.androiddevs.feature.postcomposer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.androiddevs.domain.timeline.AddPostResult
import nl.jovmit.androiddevs.domain.timeline.TimelineRepository
import nl.jovmit.androiddevs.shared.ui.extensions.update
import javax.inject.Inject

@HiltViewModel
class PostComposerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val timelineRepository: TimelineRepository,
    private val background: CoroutineDispatcher
) : ViewModel() {

    val screenState: StateFlow<PostComposerScreenState> =
        savedStateHandle.getStateFlow(POST_COMPOSER, PostComposerScreenState())

    fun updateTitle(title: String) {
        savedStateHandle.update<PostComposerScreenState>(POST_COMPOSER) {
            it.copy(
                title = title,
                isTitleBlank = false,
                isOffline = false,
                isUnavailable = false,
                isNotSignedIn = false
            )
        }
    }

    fun updateBody(body: String) {
        savedStateHandle.update<PostComposerScreenState>(POST_COMPOSER) {
            it.copy(
                body = body,
                isBodyBlank = false,
                isOffline = false,
                isUnavailable = false,
                isNotSignedIn = false
            )
        }
    }

    fun addPost() {
        val title = screenState.value.title
        val body = screenState.value.body
        val isTitleBlank = title.isBlank()
        val isBodyBlank = body.isBlank()

        if (isTitleBlank || isBodyBlank) {
            savedStateHandle.update<PostComposerScreenState>(POST_COMPOSER) {
                it.copy(isTitleBlank = isTitleBlank, isBodyBlank = isBodyBlank)
            }
            return
        }

        viewModelScope.launch {
            setLoading()
            val result = withContext(background) {
                timelineRepository.addPost(title, body)
            }
            onAddPostResult(result)
        }
    }

    private fun setLoading() {
        savedStateHandle.update<PostComposerScreenState>(POST_COMPOSER) {
            it.copy(isLoading = true)
        }
    }

    private fun onAddPostResult(result: AddPostResult) {
        savedStateHandle.update<PostComposerScreenState>(POST_COMPOSER) {
            when (result) {
                is AddPostResult.Success -> it.copy(isLoading = false, isPosted = true)
                AddPostResult.InvalidContent -> it.copy(
                    isLoading = false,
                    isTitleBlank = it.title.isBlank(),
                    isBodyBlank = it.body.isBlank()
                )
                AddPostResult.NotSignedIn -> it.copy(isLoading = false, isNotSignedIn = true)
                AddPostResult.Offline -> it.copy(isLoading = false, isOffline = true)
                AddPostResult.Unavailable -> it.copy(isLoading = false, isUnavailable = true)
            }
        }
    }

    companion object {
        private const val POST_COMPOSER = "postComposerKey"
    }
}
