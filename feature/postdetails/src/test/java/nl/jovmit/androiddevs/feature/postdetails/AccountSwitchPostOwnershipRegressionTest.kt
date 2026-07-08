package nl.jovmit.androiddevs.feature.postdetails

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.InMemoryUserSession
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.timeline.AddPostResult
import nl.jovmit.androiddevs.domain.timeline.InMemoryTimelineRepository
import nl.jovmit.androiddevs.domain.timeline.RemovePostResult
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class AccountSwitchPostOwnershipRegressionTest {

  @Test
  fun userCannotRemoveAnotherUsersPostAfterAccountSwitch() = runTest {
    val authRepository = InMemoryAuthRepository()
    val session = InMemoryUserSession()
    val timelineRepository = InMemoryTimelineRepository(session)
    val alice = authRepository.signUp(
      email = "alice@androiddevs.nl",
      password = "passWord12.",
      about = "Compose mentor"
    ).authenticatedUser()
    session.setSessionUser(alice)
    val alicePost = (timelineRepository.addPost(
      title = "How I debug recomposition spikes",
      body = "I stopped guessing and started measuring recomposition from the user-facing screen."
    ) as AddPostResult.Success).post

    session.clear()
    authRepository.signUp(
      email = "bob@androiddevs.nl",
      password = "passWord12.",
      about = "Architecture coach"
    )
    val bob = authRepository.login("bob@androiddevs.nl", "passWord12.").authenticatedUser()
    session.setSessionUser(bob)
    val viewModel = PostDetailsViewModel(
      timelineRepository = timelineRepository,
      userSession = session,
      backgroundDispatcher = Dispatchers.Unconfined
    )

    viewModel.loadPostDetails(alicePost.id)

    assertThat(viewModel.screenState.value.postItem?.post?.id).isEqualTo(alicePost.id)
    assertThat(viewModel.screenState.value.postItem?.canRemove).isFalse()
    assertThat(timelineRepository.removePost(alicePost.id)).isEqualTo(RemovePostResult.NotAuthor)
  }

  private fun AuthResult.authenticatedUser() = (this as AuthResult.Success).user
}
