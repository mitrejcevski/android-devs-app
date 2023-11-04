package nl.jovmit.androiddevs.feature.timeline

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TimelinePagingSourceTest {

    private val notLimited = false
    private val limited = true
    private val fakeData = (1..150).map { ListItem("$it", "List Item $it") }
    private val repository = InMemoryTimelineRepository(fakeData)

    @Test
    fun loadInitialPage() = runBlocking {
        val timelinePagingSource = TimelinePaging(notLimited, repository)
        val loadParams = PagingSource.LoadParams.Refresh(0, 20, false)

        val result = timelinePagingSource.load(loadParams)

        assertThat(result).isEqualTo(
            PagingSource.LoadResult.Page(
                data = fakeData.take(loadParams.loadSize),
                nextKey = loadParams.key?.plus(1),
                prevKey = null
            )
        )
    }

    @Test
    fun loadSecondPage() = runBlocking {
        val timelinePagingSource = TimelinePaging(notLimited, repository)
        val loadParams = PagingSource.LoadParams.Refresh(1, 20, false)

        val result = timelinePagingSource.load(loadParams)

        assertThat(result).isEqualTo(
            PagingSource.LoadResult.Page(
                data = fakeData.subList(20, 40),
                nextKey = loadParams.key?.plus(1),
                prevKey = loadParams.key?.minus(1)
            )
        )
    }

    @Test
    fun loadInitialPageLimited() = runBlocking {
        val limit = 4
        val timelinePagingSource = TimelinePaging(limited, repository, limit)
        val loadParams = PagingSource.LoadParams.Refresh(0, 20, false)

        val result = timelinePagingSource.load(loadParams)

        assertThat(result).isEqualTo(
            PagingSource.LoadResult.Page(
                data = fakeData.take(limit),
                nextKey = null,
                prevKey = null
            )
        )
    }

    @Test
    fun errorLoading() = runBlocking {
        val offlineRepository = ErrorTimelineRepository()
        val timelinePagingSource = TimelinePaging(notLimited, offlineRepository)
        val loadParams = PagingSource.LoadParams.Refresh(0, 20, false)

        val result = timelinePagingSource.load(loadParams)

        assertThat(result).isEqualTo(
            PagingSource.LoadResult.Error<Int, ListItem>(
                LoadingError
            )
        )
    }

    class ErrorTimelineRepository : TimelineRepository {

        override suspend fun loadTimeline(page: Int, pageSize: Int): TimelineResponse {
            return TimelineResponse.Error
        }
    }
}