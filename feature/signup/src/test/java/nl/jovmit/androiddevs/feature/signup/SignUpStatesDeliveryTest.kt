package nl.jovmit.androiddevs.feature.signup

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import nl.jovmit.androiddevs.testutils.collectStateFlow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class SignUpStatesDeliveryTest {

    private val email = "valid@email.com"
    private val password = "Pa\$woRd12."

    private val savedStateHandle = SavedStateHandle()
    private val dispatcher = Dispatchers.Unconfined

    @Test
    fun statesDeliveredInOrder() = runTest {
        val repository = InMemoryAuthRepository().apply { setOffline() }
        val viewModel = SignUpViewModel(savedStateHandle, repository, dispatcher).apply {
            updateEmail(email)
            updatePassword(password)
        }

        val deliveredStates = collectStateFlow(viewModel.screenState) {
            viewModel.signUp()
        }

        assertThat(deliveredStates).isEqualTo(
            listOf(
                SignUpScreenState(email = email, password = password, isLoading = true),
                SignUpScreenState(email = email, password = password, isOfflineError = true)
            )
        )
    }
}