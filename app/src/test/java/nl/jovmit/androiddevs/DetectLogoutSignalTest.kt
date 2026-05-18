package nl.jovmit.androiddevs

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.core.network.LogoutSignal
import nl.jovmit.androiddevs.domain.auth.InMemoryUserSession
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import nl.jovmit.androiddevs.testutils.collectSharedFlow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class DetectLogoutSignalTest {

    private val loggedOutSignal = LogoutSignal()

    @Test
    fun logoutSignalDetected() = runTest {
        val userSession = InMemoryUserSession().apply {
            setSessionUser(User("userId", "user@email.com", "about"))
        }
        val viewModel = MainAppViewModel(loggedOutSignal, userSession).apply {
            observeLoggedOut()
        }

        val observedEvent = collectSharedFlow(viewModel.loggedOut) {
            loggedOutSignal.onLoggedOut()
        }

        assertThat(observedEvent).isEqualTo(Unit)
        assertThat(userSession.sessionUser.value).isNull()
    }
}
