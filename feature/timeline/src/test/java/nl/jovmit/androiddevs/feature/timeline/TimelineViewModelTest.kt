package nl.jovmit.androiddevs.feature.timeline

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.core.network.LogoutSignal
import nl.jovmit.androiddevs.domain.auth.InMemoryUserSession
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.timeline.AddPostResult
import nl.jovmit.androiddevs.domain.timeline.PostDetailsResult
import nl.jovmit.androiddevs.domain.timeline.RemovePostResult
import nl.jovmit.androiddevs.domain.timeline.TimelineRepository
import nl.jovmit.androiddevs.domain.timeline.TimelineResult
import nl.jovmit.androiddevs.domain.timeline.data.Comment
import nl.jovmit.androiddevs.domain.timeline.data.Post
import nl.jovmit.androiddevs.domain.timeline.data.Reaction
import nl.jovmit.androiddevs.domain.timeline.data.ReactionType
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import nl.jovmit.androiddevs.testutils.collectStateFlow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class TimelineViewModelTest {

  private val maya = User("maya", "maya@androiddevs.nl", "Compose mentor")
  private val sam = User("sam", "sam@androiddevs.nl", "Architecture coach")
  private val session = InMemoryUserSession()
  private val timelineRepository = FakeTimelineRepository()

  @Test
  fun timelineExposesScannablePostRows() = runTest {
    timelineRepository.showPosts(
      listOf(
        Post(
          id = "post-compose",
          author = maya,
          title = "How I debug recomposition spikes",
          body = "I stopped guessing and started measuring recomposition from the user-facing screen.",
          createdAtMillis = 1_700_000_000_000L,
          imageUrls = listOf("https://images.example.com/compose.png", "https://images.example.com/ignored.png"),
          comments = listOf(
            Comment("comment-1", sam, "That identity detail is easy to miss.", 1_700_000_000_001L),
            Comment("comment-2", sam, "This belongs in our review checklist.", 1_700_000_000_002L)
          ),
          reactions = listOf(
            Reaction(sam, ReactionType.LIKE),
            Reaction(maya, ReactionType.SUPPORT)
          )
        )
      )
    )
    val viewModel = TimelineViewModel(
      logoutSignal = LogoutSignal(),
      timelineRepository = timelineRepository,
      userSession = session,
      background = Dispatchers.Unconfined
    )

    val states = collectStateFlow(viewModel.screenState, dropInitialValue = false) {}

    val post = states.last().posts.single()
    assertThat(post.id).isEqualTo("post-compose")
    assertThat(post.author).isEqualTo("maya@androiddevs.nl")
    assertThat(post.title).isEqualTo("How I debug recomposition spikes")
    assertThat(post.body).contains("measuring recomposition")
    assertThat(post.imageUrl).isEqualTo("https://images.example.com/compose.png")
    assertThat(post.commentCount).isEqualTo(2)
    assertThat(post.reactionCount).isEqualTo(2)
  }

  private class FakeTimelineRepository : TimelineRepository {

    private val timelineResults = MutableStateFlow<TimelineResult>(TimelineResult.Success(emptyList()))
    override val timeline: Flow<TimelineResult> = timelineResults

    fun showPosts(posts: List<Post>) {
      timelineResults.value = TimelineResult.Success(posts)
    }

    override suspend fun getPost(postId: String): PostDetailsResult {
      error("Not needed for this test")
    }

    override suspend fun addPost(title: String, body: String): AddPostResult {
      error("Not needed for this test")
    }

    override suspend fun removePost(postId: String): RemovePostResult {
      error("Not needed for this test")
    }
  }
}
