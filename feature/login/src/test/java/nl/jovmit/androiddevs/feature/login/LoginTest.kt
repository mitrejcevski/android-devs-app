package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class LoginTest {

    private val alice = User(email = "alice@app.com")
    private val bob = User(email = "bob@app.com")

    private val usersCatalog = InMemoryUsersCatalog(listOf(bob, alice))
    private val savedStateHandle = SavedStateHandle()

    @Test
    fun userLoggedIn() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(alice.email)
            updatePassword(":password:")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(loggedInUser = alice))
    }

    @Test
    fun anotherLoggedInUser() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(bob.email)
            updatePassword("bobsPassword")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(loggedInUser = bob))
    }

    @Test
    fun noUserFound() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail("valid@email.com")
            updatePassword("validPassword")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }
}
















