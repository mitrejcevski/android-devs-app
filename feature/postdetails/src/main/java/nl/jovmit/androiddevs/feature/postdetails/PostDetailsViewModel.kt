package nl.jovmit.androiddevs.feature.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val backgroundDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _screenState = MutableStateFlow(PostDetailsScreenState())
    private val postDetailsArgs = PostDetailsArgs(savedStateHandle)

    val screenState = _screenState.asStateFlow()

    fun loadPostDetails() {
        val postId = postDetailsArgs.postId
        viewModelScope.launch {
            setLoading()
            val result = withContext(backgroundDispatcher) {
                delay(1500) //mimic loading data
                "Post $postId"
            }
            onPostResult(result)
        }
    }

    private fun setLoading() {
        _screenState.update { it.copy(isLoading = true) }
    }

    private fun onPostResult(result: String) {
        _screenState.update { it.copy(title = result, isLoading = false) }
    }
}