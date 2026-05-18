package nl.jovmit.androiddevs.feature.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.androiddevs.core.network.LogoutSignal
import nl.jovmit.androiddevs.domain.auth.UserSession
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.timeline.TimelineRepository
import nl.jovmit.androiddevs.domain.timeline.TimelineResult
import nl.jovmit.androiddevs.domain.timeline.data.Post
import nl.jovmit.androiddevs.domain.timeline.data.ReactionType
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
  private val logoutSignal: LogoutSignal,
  private val timelineRepository: TimelineRepository,
  private val userSession: UserSession,
  private val background: CoroutineDispatcher
) : ViewModel() {

  val screenState: StateFlow<TimelineScreenState> = combine(
    timelineRepository.timeline,
    userSession.sessionUser
  ) { timelineResult, sessionUser ->
    timelineResult.toScreenState(sessionUser)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = TimelineScreenState(isLoading = true)
  )

  fun removePost(postId: String) {
    viewModelScope.launch {
      withContext(background) {
        timelineRepository.removePost(postId)
      }
    }
  }

  fun doLogout() {
    logoutSignal.onLoggedOut()
  }

  private fun TimelineResult.toScreenState(sessionUser: User?): TimelineScreenState {
    return when (this) {
      TimelineResult.Offline -> TimelineScreenState(isLoading = false, isOffline = true)
      TimelineResult.Unavailable -> TimelineScreenState(isLoading = false, isUnavailable = true)
      is TimelineResult.Success -> TimelineScreenState(
        isLoading = false,
        posts = posts.map { it.toTimelinePostItem(sessionUser) }.toPersistentList()
      )
    }
  }

  private fun Post.toTimelinePostItem(sessionUser: User?): TimelinePostItem {
    return TimelinePostItem(
      postItem = this,
      canRemove = sessionUser?.userId == author.userId
    )
  }
}

data class TimelineScreenState(
  val isLoading: Boolean = false,
  val isOffline: Boolean = false,
  val isUnavailable: Boolean = false,
  val posts: ImmutableList<TimelinePostItem> = persistentListOf()
)

data class TimelinePostItem(
  val postItem: Post,
  val canRemove: Boolean
) {
  val id: String = postItem.id
  val author: String = postItem.author.email
  val title: String = postItem.title
  val body: String = postItem.body
  val imageUrl: String? = postItem.imageUrls.firstOrNull()
  val commentCount: Int = postItem.comments.size
  val reactionCount: Int = postItem.reactions.size
  val reactionSummary: Map<ReactionType, Int> = postItem.reactions.groupingBy { it.type }.eachCount()
}
