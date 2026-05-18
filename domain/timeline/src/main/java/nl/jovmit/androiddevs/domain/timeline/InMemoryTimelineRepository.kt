package nl.jovmit.androiddevs.domain.timeline

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.jovmit.androiddevs.domain.auth.UserSession
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.timeline.data.Comment
import nl.jovmit.androiddevs.domain.timeline.data.Post
import nl.jovmit.androiddevs.domain.timeline.data.Reaction
import nl.jovmit.androiddevs.domain.timeline.data.ReactionType
import nl.jovmit.androiddevs.domain.timeline.data.Tag
import java.util.UUID
import javax.inject.Inject

class InMemoryTimelineRepository @Inject constructor(
    private val userSession: UserSession
) : TimelineRepository {

    private var posts = seedPosts()
    private var isOffline = false
    private var isUnavailable = false

    private val _timeline = MutableStateFlow<TimelineResult>(TimelineResult.Success(sortedPosts()))
    override val timeline = _timeline.asStateFlow()

    override suspend fun getPost(postId: String): PostDetailsResult {
        if (isOffline) return PostDetailsResult.Offline
        if (isUnavailable) return PostDetailsResult.Unavailable

        val post = posts.firstOrNull { it.id == postId }
        return post?.let(PostDetailsResult::Success) ?: PostDetailsResult.PostNotFound
    }

    override suspend fun addPost(title: String, body: String): AddPostResult {
        if (isOffline) return AddPostResult.Offline
        if (isUnavailable) return AddPostResult.Unavailable

        val sessionUser = userSession.sessionUser.value ?: return AddPostResult.NotSignedIn
        if (title.isBlank() || body.isBlank()) return AddPostResult.InvalidContent

        val post = Post(
            id = UUID.randomUUID().toString(),
            author = sessionUser,
            title = title.trim(),
            body = body.trim(),
            createdAtMillis = System.currentTimeMillis()
        )
        posts = posts + post
        emitPosts()
        return AddPostResult.Success(post)
    }

    override suspend fun removePost(postId: String): RemovePostResult {
        if (isOffline) return RemovePostResult.Offline
        if (isUnavailable) return RemovePostResult.Unavailable

        val sessionUser = userSession.sessionUser.value ?: return RemovePostResult.NotSignedIn
        val post = posts.firstOrNull { it.id == postId } ?: return RemovePostResult.PostNotFound
        if (post.author.userId != sessionUser.userId) return RemovePostResult.NotAuthor

        posts = posts.filterNot { it.id == postId }
        emitPosts()
        return RemovePostResult.Success
    }

    fun setOffline() {
        isOffline = true
        isUnavailable = false
        _timeline.value = TimelineResult.Offline
    }

    fun setUnavailable() {
        isUnavailable = true
        isOffline = false
        _timeline.value = TimelineResult.Unavailable
    }

    fun setAvailable() {
        isOffline = false
        isUnavailable = false
        emitPosts()
    }

    private fun emitPosts() {
        _timeline.value = TimelineResult.Success(sortedPosts())
    }

    private fun sortedPosts() = posts.sortedByDescending { it.createdAtMillis }

    private companion object {
        private val maya = User("seed-user-maya", "maya@androiddevs.nl", "Compose mentor")
        private val sam = User("seed-user-sam", "sam@androiddevs.nl", "Architecture coach")
        private val priya = User("seed-user-priya", "priya@androiddevs.nl", "Testing specialist")
        private val leo = User("seed-user-leo", "leo@androiddevs.nl", "Performance tinkerer")
        private val nina = User("seed-user-nina", "nina@androiddevs.nl", "Career guide")

        private const val DAY = 86_400_000L
        private const val NOW = 1_700_000_000_000L

        private fun seedPosts() = listOf(
            Post(
                id = "post-compose-recomposition",
                author = maya,
                title = "How I debug recomposition spikes",
                body = "I stopped guessing and started adding small counters around the expensive composables. The surprise was that the slow part was not the list, but a derived label that changed identity on every state update.",
                createdAtMillis = NOW,
                imageUrls = listOf("https://images.unsplash.com/photo-1515879218367-8466d910aaa4"),
                tags = listOf(Tag("Compose"), Tag("Performance")),
                comments = listOf(
                    Comment("comment-compose-1", sam, "That identity detail is the one people miss most often.", NOW - 4_000_000L),
                    Comment("comment-compose-2", priya, "I use this as a code review checklist item now.", NOW - 2_000_000L)
                ),
                reactions = listOf(
                    Reaction(sam, ReactionType.INSIGHTFUL),
                    Reaction(priya, ReactionType.LIKE),
                    Reaction(leo, ReactionType.SUPPORT),
                    Reaction(nina, ReactionType.LOVE)
                )
            ),
            Post(
                id = "post-room-migrations",
                author = sam,
                title = "Room migration checklist before release",
                body = "The migration test is the last thing I run before handing off a release candidate. Create old schema, insert realistic data, migrate, then assert the shape your UI actually needs.",
                createdAtMillis = NOW - DAY,
                imageUrls = emptyList(),
                tags = listOf(Tag("Persistence"), Tag("Room"), Tag("Release")),
                comments = listOf(
                    Comment("comment-room-1", maya, "The realistic data part changed how I write these tests.", NOW - DAY + 3_000_000L)
                ),
                reactions = listOf(
                    Reaction(maya, ReactionType.LIKE),
                    Reaction(priya, ReactionType.CELEBRATE),
                    Reaction(leo, ReactionType.INSIGHTFUL)
                )
            ),
            Post(
                id = "post-structured-concurrency",
                author = priya,
                title = "Interview prep: explaining structured concurrency",
                body = "My favorite answer is to describe it as ownership for async work. If a screen starts the work, the screen should also own cancellation unless the use case has a longer lifetime.",
                createdAtMillis = NOW - (2 * DAY),
                imageUrls = listOf("https://images.unsplash.com/photo-1498050108023-c5249f4df085"),
                tags = listOf(Tag("Coroutines"), Tag("Career")),
                comments = listOf(
                    Comment("comment-coroutines-1", nina, "This is such a clear interview framing.", NOW - (2 * DAY) + 6_000_000L),
                    Comment("comment-coroutines-2", leo, "I pair it with supervisor scope examples.", NOW - (2 * DAY) + 7_000_000L)
                ),
                reactions = listOf(
                    Reaction(maya, ReactionType.LOVE),
                    Reaction(sam, ReactionType.INSIGHTFUL),
                    Reaction(leo, ReactionType.LIKE),
                    Reaction(nina, ReactionType.SUPPORT)
                )
            ),
            Post(
                id = "post-startup-performance",
                author = leo,
                title = "Cold start wins from boring measurements",
                body = "The biggest improvement this week came from deleting work from Application startup. Trace first, then move anything user-invisible behind the first real screen.",
                createdAtMillis = NOW - (3 * DAY),
                imageUrls = listOf("https://images.unsplash.com/photo-1551288049-bebda4e38f71"),
                tags = listOf(Tag("Performance"), Tag("Tooling")),
                comments = emptyList(),
                reactions = listOf(
                    Reaction(maya, ReactionType.SUPPORT),
                    Reaction(sam, ReactionType.LIKE)
                )
            ),
            Post(
                id = "post-salary-story",
                author = nina,
                title = "The promotion packet is a debugging artifact",
                body = "Write down the production problems you removed, the ambiguity you reduced, and the engineers you unblocked. That record beats trying to remember impact at review time.",
                createdAtMillis = NOW - (4 * DAY),
                imageUrls = emptyList(),
                tags = listOf(Tag("Career"), Tag("Leadership")),
                comments = listOf(
                    Comment("comment-career-1", priya, "I wish I had learned this two jobs earlier.", NOW - (4 * DAY) + 8_000_000L)
                ),
                reactions = listOf(
                    Reaction(maya, ReactionType.LOVE),
                    Reaction(sam, ReactionType.SUPPORT),
                    Reaction(priya, ReactionType.CELEBRATE),
                    Reaction(leo, ReactionType.LIKE)
                )
            )
        )
    }
}
