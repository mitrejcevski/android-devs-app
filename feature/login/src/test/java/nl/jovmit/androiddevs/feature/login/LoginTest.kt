package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class LoginTest {

    private val savedStateHandle = SavedStateHandle()

    @Test
    fun userLoggedIn() {
        val alice = User(email = "alice@app.com")
        val viewModel = LoginViewModel(savedStateHandle).apply {
            updateEmail(alice.email)
            updatePassword(":password:")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(loggedInUser = alice))
    }

    @Test
    fun anotherLoggedInUser() {
        val bob = User(email = "bob@app.com")
        val viewModel = LoginViewModel(savedStateHandle).apply {
            updateEmail(bob.email)
            updatePassword("bobsPassword")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(loggedInUser = bob))
    }
}