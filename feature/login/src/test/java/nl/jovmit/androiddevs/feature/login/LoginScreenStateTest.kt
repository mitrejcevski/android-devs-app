package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
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
    private val backgroundDispatcher = Dispatchers.Unconfined

    @Test
    fun defaultScreenState() {
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState())
    }

    @Test
    fun updateEmail() {
        val updatedEmail = ":some email:"
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher)

        viewModel.updateEmail(updatedEmail)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(email = updatedEmail))
    }

    @Test
    fun updatePassword() {
        val newPassword = ":a password:"
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher)

        viewModel.updatePassword(newPassword)

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState(password = newPassword))
    }

    @Test
    fun resetWrongEmailState() {
        val initialEmailValue = "something"
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail(initialEmailValue)
            login()
        }

        viewModel.updateEmail("$initialEmailValue@")

        assertThat(viewModel.screenState.value.isWrongEmailFormat)
            .isEqualTo(false)
    }

    @Test
    fun resetWrongPasswordState() {
        val initialPasswordValue = "pass"
        val viewModel = LoginViewModel(savedStateHandle, usersCatalog, backgroundDispatcher).apply {
            updateEmail("bob@app.com")
            updatePassword(initialPasswordValue)
            login()
        }

        viewModel.updatePassword("$initialPasswordValue.")

        assertThat(viewModel.screenState.value.isBadPasswordFormat)
            .isEqualTo(false)
    }
}















