package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.UserBuilder.Companion.aUser
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class LoginTest {

    private val alycia = aUser().withEmail("alycia@app.com").build()
    private val alice = aUser().withEmail("alice@app.com").build()
    private val alicePassword = ":Passw0rd:"
    private val bob = aUser().withEmail("bob@app.com").build()
    private val bobPassword = "bobsPassword1"
    private val unknownEmail = "valid@email.com"
    private val usersForPassword = mapOf(
        alicePassword to listOf(alice, alycia),
        bobPassword to listOf(bob)
    )
    private val usersCatalog = InMemoryAuthRepository(usersForPassword = usersForPassword)
    private val savedStateHandle = SavedStateHandle()
    private val backgroundDispatcher = Dispatchers.Unconfined

    @Test
    fun userLoggedIn() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(alice.email)
            updatePassword(alicePassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value.loggedInUser).isEqualTo(alice.email)
    }

    @Test
    fun providedIncorrectPassword() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(alice.email)
            updatePassword("anythingBut$alicePassword")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }

    @Test
    fun providedIncorrectEmail() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(unknownEmail)
            updatePassword(alicePassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }

    @Test
    fun loginWithUserHavingSamePassword() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(alycia.email)
            updatePassword(alicePassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value.loggedInUser).isEqualTo(alycia.email)
    }

    @Test
    fun anotherLoggedInUser() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(bob.email)
            updatePassword(bobPassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value.loggedInUser).isEqualTo(bob.email)
    }

    @Test
    fun noUserFound() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(unknownEmail)
            updatePassword("validPassword1")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(wrongCredentials = true))
    }

    @Test
    fun attemptToLoginWithIncorrectEmail() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(" ")
            updatePassword(bobPassword)
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(isWrongEmailFormat = true))
    }

    @Test
    fun attemptToLoginWithIncorrectPassword() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(bob.email)
            updatePassword("wrong")
        }

        viewModel.login()

        assertThat(viewModel.screenState.value)
            .isEqualTo(viewModel.screenState.value.copy(isBadPasswordFormat = true))
    }
}















