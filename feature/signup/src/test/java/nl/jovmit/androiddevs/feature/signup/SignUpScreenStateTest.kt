package nl.jovmit.androiddevs.feature.signup

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState
import org.junit.jupiter.api.Test

class SignUpScreenStateTest {

    private val authRepository = InMemoryAuthRepository()
    private val savedStateHandle = SavedStateHandle()

    @Test
    fun `email value updating`() {
        val newEmailValue = "email@"
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, Dispatchers.Unconfined)

        viewModel.updateEmail(newEmailValue)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(email = newEmailValue)
        )
    }

    @Test
    fun `password value updating`() = runTest {
        val newValue = ":irrelevant:"
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, Dispatchers.Unconfined)

        viewModel.updatePassword(newValue)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(password = newValue)
        )
    }

    @Test
    fun `about value updating`() {
        val newValue = ":dunno:"
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, Dispatchers.Unconfined)

        viewModel.updateAbout(newValue)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(about = newValue)
        )
    }
}