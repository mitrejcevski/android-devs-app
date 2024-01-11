package nl.jovmit.androiddevs.feature.timeline

import androidx.paging.PagingSource
import androidx.paging.PagingState

class TimelinePaging(
    private val showLimitedContent: Boolean,
    private val timelineRepository: TimelineRepository,
    private val itemsLimit: Int = 3
) : PagingSource<Int, ListItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        val page = params.key ?: 0
        val pageSize = params.loadSize
        return when (val timelineResponse = timelineRepository.loadTimeline(page, pageSize)) {
            is TimelineResponse.Success -> {
                val pageData = timelineResponse.data
                LoadResult.Page(
                    data = if (showLimitedContent) pageData.take(itemsLimit) else pageData,
                    prevKey = timelineResponse.previousPage,
                    nextKey = if (showLimitedContent) null else timelineResponse.nextPage
                )
            }

            is TimelineResponse.Error -> {
                LoadResult.Error(LoadingError)
            }
        }
    }
}