package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class LoginScreenStateTest {

    private val savedStateHandle = SavedStateHandle()
    private val usersCatalog = InMemoryUsersCatalog(
        mapOf(
            ":password:" to listOf(
                User(email = "bob@app.com"),
                User(email = "alice@app.com")
            ),
            "bobsPassword" to listOf(
                User(email = "bob@app.com"),
                User(email = "alice@app.com")
            )
        )
    )

    @Test
    fun defaultScreenState() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState())
    }

    @Test
    fun updateEmail() {
        val updatedEmail = ":some email:"
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog)

        viewModel.updateEmail(updatedEmail)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(email = updatedEmail))
    }

    @Test
    fun updatePassword() {
        val newPassword = ":a password:"
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog)

        viewModel.updatePassword(newPassword)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(password = newPassword))
    }
}