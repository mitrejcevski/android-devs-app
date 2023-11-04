package nl.jovmit.androiddevs.feature.timeline

import androidx.paging.testing.asSnapshot
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class TimelineStateTest {

    private val fakeData = (1..150).map { ListItem("$it", "List Item $it") }
    private val repository = InMemoryTimelineRepository(fakeData)

    @Test
    fun defaultState() {
        val viewModel = TimelineViewModel(repository)

        assertThat(viewModel.screenState.value).isEqualTo(TimelineScreenState())
    }

    @Test
    fun toggleLimit() {
        val limitContent = true
        val viewModel = TimelineViewModel(repository)

        viewModel.toggleLimit(limitContent)

        assertThat(viewModel.screenState.value).isEqualTo(
            TimelineScreenState(showLimitedContent = limitContent)
        )
    }

    @Test
    fun initialTimelinePosts() = runTest {
        val viewModel = TimelineViewModel(repository)

        val result = viewModel.timelinePosts.asSnapshot {
            refresh()
        }

        assertThat(result).isEqualTo(fakeData.take(20))
    }

    @Test
    fun loadFirstTwoPagesOfTimeline() = runTest {
        val viewModel = TimelineViewModel(repository)

        val result = viewModel.timelinePosts.asSnapshot {
            refresh()
            scrollTo(20)
        }

        assertThat(result).isEqualTo(fakeData.take(40))
    }

    @Test
    fun limitLoadedContent() = runTest {
        val viewModel = TimelineViewModel(repository)
        viewModel.toggleLimit(true)

        val result = viewModel.timelinePosts.asSnapshot {
            refresh()
        }

        assertThat(result).isEqualTo(fakeData.take(3))
    }
}