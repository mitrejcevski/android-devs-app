package nl.jovmit.androiddevs.feature.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.androiddevs.domain.auth.UserSession
import nl.jovmit.androiddevs.domain.timeline.PostDetailsResult
import nl.jovmit.androiddevs.domain.timeline.RemovePostResult
import nl.jovmit.androiddevs.domain.timeline.TimelineRepository
import nl.jovmit.androiddevs.domain.timeline.data.Post
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val timelineRepository: TimelineRepository,
    private val userSession: UserSession,
    private val backgroundDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _screenState = MutableStateFlow(PostDetailsScreenState())
    private val postDetailsArgs = PostDetailsDestination(savedStateHandle)

    val screenState = _screenState.asStateFlow()

    fun loadPostDetails() {
        val postId = postDetailsArgs.postId
        viewModelScope.launch {
            setLoading()
            val result = withContext(backgroundDispatcher) {
                timelineRepository.getPost(postId)
            }
            onPostResult(result)
        }
    }

    fun removePost() {
        val postId = _screenState.value.postItem?.post?.id ?: return
        viewModelScope.launch {
            val result = withContext(backgroundDispatcher) {
                timelineRepository.removePost(postId)
            }
            if (result == RemovePostResult.Success) {
                _screenState.update { it.copy(isRemoved = true) }
            }
        }
    }

    private fun setLoading() {
        _screenState.update {
            PostDetailsScreenState(isLoading = true)
        }
    }

    private fun onPostResult(result: PostDetailsResult) {
        _screenState.update {
            when (result) {
                is PostDetailsResult.Success -> PostDetailsScreenState(
                    postItem = result.post.toPostDetailsItem()
                )
                PostDetailsResult.PostNotFound -> PostDetailsScreenState(isNotFound = true)
                PostDetailsResult.Offline -> PostDetailsScreenState(isOffline = true)
                PostDetailsResult.Unavailable -> PostDetailsScreenState(isUnavailable = true)
            }
        }
    }

    private fun Post.toPostDetailsItem(): PostDetailsItem {
        return PostDetailsItem(
            post = this,
            canRemove = userSession.sessionUser.value?.userId == author.userId
        )
    }
}
