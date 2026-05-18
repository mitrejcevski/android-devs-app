package nl.jovmit.androiddevs.domain.timeline

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.InMemoryUserSession
import nl.jovmit.androiddevs.domain.auth.data.User
import org.junit.jupiter.api.Test

class InMemoryTimelineRepositoryTest {

    private val session = InMemoryUserSession()
    private val repository = InMemoryTimelineRepository(session)

    @Test
    fun seedPostsAreShownNewestFirst() = runTest {
        val result = repository.timeline.first()

        val posts = (result as TimelineResult.Success).posts
        assertThat(posts.map { it.id }).containsExactly(
            "post-compose-recomposition",
            "post-room-migrations",
            "post-structured-concurrency",
            "post-startup-performance",
            "post-salary-story"
        ).inOrder()
    }

    @Test
    fun addedPostIsAuthoredBySessionUserAndShownFirst() = runTest {
        val user = User("alice", "alice@androiddevs.nl", "about")
        session.setSessionUser(user)

        val result = repository.addPost("Compose state", "Keep state close to where it changes.")

        val addedPost = (result as AddPostResult.Success).post
        val posts = (repository.timeline.first() as TimelineResult.Success).posts
        assertThat(addedPost.author).isEqualTo(user)
        assertThat(posts.first()).isEqualTo(addedPost)
    }

    @Test
    fun addingPostRequiresActiveSession() = runTest {
        val result = repository.addPost("Compose state", "Keep state close to where it changes.")

        assertThat(result).isEqualTo(AddPostResult.NotSignedIn)
    }

    @Test
    fun removingPostRequiresAuthoringUser() = runTest {
        val alice = User("alice", "alice@androiddevs.nl", "about")
        val bob = User("bob", "bob@androiddevs.nl", "about")
        session.setSessionUser(alice)
        val post = (repository.addPost("Compose state", "Keep state close to where it changes.") as AddPostResult.Success).post
        session.setSessionUser(bob)

        val result = repository.removePost(post.id)

        assertThat(result).isEqualTo(RemovePostResult.NotAuthor)
    }

    @Test
    fun removingPostRemovesItFromTimeline() = runTest {
        val user = User("alice", "alice@androiddevs.nl", "about")
        session.setSessionUser(user)
        val post = (repository.addPost("Compose state", "Keep state close to where it changes.") as AddPostResult.Success).post

        val result = repository.removePost(post.id)

        val posts = (repository.timeline.first() as TimelineResult.Success).posts
        assertThat(result).isEqualTo(RemovePostResult.Success)
        assertThat(posts.map { it.id }).doesNotContain(post.id)
    }

    @Test
    fun offlineStateWinsBeforeSessionAndAuthChecks() = runTest {
        repository.setOffline()

        val addResult = repository.addPost("Compose state", "Keep state close to where it changes.")
        val removeResult = repository.removePost("post-compose-recomposition")
        val detailsResult = repository.getPost("post-compose-recomposition")

        assertThat(addResult).isEqualTo(AddPostResult.Offline)
        assertThat(removeResult).isEqualTo(RemovePostResult.Offline)
        assertThat(detailsResult).isEqualTo(PostDetailsResult.Offline)
    }

    @Test
    fun unavailableStateAffectsAllPostOperations() = runTest {
        repository.setUnavailable()

        val addResult = repository.addPost("Compose state", "Keep state close to where it changes.")
        val removeResult = repository.removePost("post-compose-recomposition")
        val detailsResult = repository.getPost("post-compose-recomposition")

        assertThat(addResult).isEqualTo(AddPostResult.Unavailable)
        assertThat(removeResult).isEqualTo(RemovePostResult.Unavailable)
        assertThat(detailsResult).isEqualTo(PostDetailsResult.Unavailable)
    }
}
