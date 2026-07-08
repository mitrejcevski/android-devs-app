package nl.jovmit.androiddevs.feature.postdetails

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.InMemoryUserSession
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.timeline.AddPostResult
import nl.jovmit.androiddevs.domain.timeline.PostDetailsResult
import nl.jovmit.androiddevs.domain.timeline.RemovePostResult
import nl.jovmit.androiddevs.domain.timeline.TimelineRepository
import nl.jovmit.androiddevs.domain.timeline.TimelineResult
import nl.jovmit.androiddevs.domain.timeline.data.Post
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class PostDetailsViewModelTest {

  private val maya = User("maya", "maya@androiddevs.nl", "Compose mentor")
  private val sam = User("sam", "sam@androiddevs.nl", "Architecture coach")
  private val post = Post(
    id = "post-compose",
    author = maya,
    title = "How I debug recomposition spikes",
    body = "I stopped guessing and started measuring recomposition from the user-facing screen.",
    createdAtMillis = 1_700_000_000_000L
  )
  private val session = InMemoryUserSession()
  private val timelineRepository = FakeTimelineRepository(post)

  @Test
  fun deleteVisibilityFollowsActiveSessionUser() = runTest {
    session.setSessionUser(maya)
    val viewModel = PostDetailsViewModel(
      timelineRepository = timelineRepository,
      userSession = session,
      backgroundDispatcher = Dispatchers.Unconfined
    )

    viewModel.loadPostDetails(post.id)

    assertThat(viewModel.screenState.value.postItem?.canRemove).isTrue()

    session.setSessionUser(sam)

    assertThat(viewModel.screenState.value.postItem?.canRemove).isFalse()
  }

  @Test
  fun deleteHiddenWhenActiveSessionUserIsNotTheAuthor() = runTest {
    session.setSessionUser(sam)
    val viewModel = PostDetailsViewModel(
      timelineRepository = timelineRepository,
      userSession = session,
      backgroundDispatcher = Dispatchers.Unconfined
    )

    viewModel.loadPostDetails(post.id)

    assertThat(viewModel.screenState.value.postItem?.canRemove).isFalse()
  }

  private class FakeTimelineRepository(
    private val post: Post
  ) : TimelineRepository {

    override val timeline: Flow<TimelineResult> = MutableStateFlow(TimelineResult.Success(emptyList()))

    override suspend fun getPost(postId: String): PostDetailsResult {
      return if (postId == post.id) {
        PostDetailsResult.Success(post)
      } else {
        PostDetailsResult.PostNotFound
      }
    }

    override suspend fun addPost(title: String, body: String): AddPostResult {
      error("Not needed for this test")
    }

    override suspend fun removePost(postId: String): RemovePostResult {
      error("Not needed for this test")
    }
  }
}
