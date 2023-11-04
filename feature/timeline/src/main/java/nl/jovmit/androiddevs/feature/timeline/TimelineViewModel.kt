package nl.jovmit.androiddevs.feature.timeline

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val repository: TimelineRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(TimelineScreenState())
    val screenState = _screenState.asStateFlow()

    val timelinePosts = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 1,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            TimelinePaging(screenState.value.showLimitedContent, repository)
        }
    ).flow

    fun toggleLimit(limitContent: Boolean) {
        _screenState.update { it.copy(showLimitedContent = limitContent) }
    }
}