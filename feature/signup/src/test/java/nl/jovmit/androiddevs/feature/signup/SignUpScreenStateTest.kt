package nl.jovmit.androiddevs.feature.signup

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState
import org.junit.jupiter.api.Test

class SignUpScreenStateTest {

    @Test
    fun `email value updating`() {
        val newEmailValue = "email@"
        val viewModel = SignUpViewModel()

        viewModel.updateEmail(newEmailValue)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(email = newEmailValue)
        )
    }

    @Test
    fun `password value updating`() = runTest {
        val newValue = ":irrelevant:"
        val viewModel = SignUpViewModel()

        viewModel.updatePassword(newValue)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(password = newValue)
        )
    }
}