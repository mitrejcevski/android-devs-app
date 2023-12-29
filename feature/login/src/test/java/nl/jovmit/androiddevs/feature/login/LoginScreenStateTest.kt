package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class LoginScreenStateTest {

    private val savedStateHandle = SavedStateHandle()

    @Test
    fun defaultScreenState() {
        val viewModel = LoginViewModel(savedStateHandle, InMemoryUsersCatalog(
            mapOf<String, List<User>>(
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
        )

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState())
    }

    @Test
    fun updateEmail() {
        val updatedEmail = ":some email:"
        val viewModel = LoginViewModel(savedStateHandle, InMemoryUsersCatalog(
            mapOf<String, List<User>>(
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
        )

        viewModel.updateEmail(updatedEmail)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(email = updatedEmail))
    }

    @Test
    fun updatePassword() {
        val newPassword = ":a password:"
        val viewModel = LoginViewModel(savedStateHandle, InMemoryUsersCatalog(
            mapOf<String, List<User>>(
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
        )

        viewModel.updatePassword(newPassword)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(password = newPassword))
    }
}