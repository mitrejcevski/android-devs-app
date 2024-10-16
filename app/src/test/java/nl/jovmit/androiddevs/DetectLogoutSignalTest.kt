package nl.jovmit.androiddevs

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.core.network.LogoutSignal
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import nl.jovmit.androiddevs.testutils.collectSharedFlow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class DetectLogoutSignalTest {

    private val loggedOutSignal = LogoutSignal()

    @Test
    fun logoutSignalDetected() = runTest {
        val viewModel = MainAppViewModel(loggedOutSignal).apply {
            observeLoggedOut()
        }

        val observedEvent = collectSharedFlow(viewModel.loggedOut) {
            loggedOutSignal.onLoggedOut()
        }

        assertThat(observedEvent).isEqualTo(Unit)
    }
}