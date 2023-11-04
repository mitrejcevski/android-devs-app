package nl.jovmit.androiddevs.feature.timeline

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow(TimelineScreenState())
    val screenState = _screenState.asStateFlow()

    private val listItems = (1..150).map { ListItem(it.toString(), "Item $it") }
    val timelinePosts = flowOf(PagingData.from(listItems))

    fun toggleLimit(limitContent: Boolean) {
        _screenState.update { it.copy(showLimitedContent = limitContent) }
    }
}