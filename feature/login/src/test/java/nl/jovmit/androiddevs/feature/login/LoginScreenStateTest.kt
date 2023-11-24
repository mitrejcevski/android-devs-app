package nl.jovmit.androiddevs.feature.login

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Test

class LoginScreenStateTest {

    @Test
    fun defaultScreenState() {
        val viewModel = LoginViewModel()

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState())
    }

    @Test
    fun updateEmail() {
        val updatedEmail = ":some email:"
        val viewModel = LoginViewModel()

        viewModel.updateEmail(updatedEmail)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(email = updatedEmail))
    }

    @Test
    fun updatePassword() {
        val newPassword = ":a password:"
        val viewModel = LoginViewModel()

        viewModel.updatePassword(newPassword)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(password = newPassword))
    }
}