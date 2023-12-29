package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class LoginTest {

    private val alycia = User(email = "alycia@app.com")
    private val alice = User(email = "alice@app.com")
    private val alicePassword = ":password:"
    private val bob = User(email = "bob@app.com")
    private val bobPassword = "bobsPassword"
    private val unknownEmail = "valid@email.com"
    private val usersForPassword = mapOf(
        alicePassword to listOf(alice, alycia),
        bobPassword to listOf(bob)
    )
    private val usersCatalog = InMemoryUsersCatalog(usersForPassword)

    private val savedStateHandle = SavedStateHandle()

    @Test
    fun userLoggedIn() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(alice.email)
            updatePassword(alicePassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(loggedInUser = alice))
    }

    @Test
    fun providedIncorrectPassword() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(alice.email)
            updatePassword("anythingBut$alicePassword")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }

    @Test
    fun providedIncorrectEmail() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(unknownEmail)
            updatePassword(alicePassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }

    @Test
    fun loginWithUserHavingSamePassword() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(alycia.email)
            updatePassword(alicePassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value.loggedInUser)
            .isEqualTo(alycia)
    }

    @Test
    fun anotherLoggedInUser() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(bob.email)
            updatePassword(bobPassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(loggedInUser = bob))
    }

    @Test
    fun noUserFound() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog).apply {
            updateEmail(unknownEmail)
            updatePassword("validPassword")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }
}