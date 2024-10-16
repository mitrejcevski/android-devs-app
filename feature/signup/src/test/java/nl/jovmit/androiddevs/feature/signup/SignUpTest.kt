package nl.jovmit.androiddevs.feature.signup

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState
import nl.jovmit.androiddevs.testutils.CoroutineTestExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(CoroutineTestExtension::class)
class SignUpTest {

    // - contract test to make sure prod code is aligned with the fake

    private val validEmail = "email@email.com"
    private val validPassword = "ValidP@ssword1"

    private val authRepository = InMemoryAuthRepository()
    private val savedStateHandle = SavedStateHandle()
    private val coroutineDispatcher = Dispatchers.Unconfined

    @Test
    fun invalidEmail() {
        val email = "invalid email format"
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, coroutineDispatcher)

        viewModel.signUp(email = email)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(
                email = email,
                incorrectEmailFormat = true,
                incorrectPasswordFormat = true
            )
        )
    }

    @Test
    fun invalidPassword() {
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, coroutineDispatcher)

        viewModel.signUp(email = validEmail)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(email = validEmail, incorrectPasswordFormat = true)
        )
    }

    @Test
    fun invalidEmailWithValidPassword() {
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, coroutineDispatcher)

        viewModel.signUp(password = validPassword)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(password = validPassword, incorrectEmailFormat = true)
        )
    }

    @Test
    fun signedUpSuccessfully() {
        val viewModel = SignUpViewModel(savedStateHandle, authRepository, coroutineDispatcher)

        viewModel.signUp(validEmail, validPassword)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(
                email = validEmail,
                password = validPassword,
                isSignedUp = true
            )
        )
    }

    @Test
    fun attemptToSignUpWithKnownEmail() {
        val newPassword = "another$validPassword"
        val repository = InMemoryAuthRepository(
            usersForPassword = mapOf(validPassword to listOf(User("userId", validEmail, "")))
        )
        val viewModel = SignUpViewModel(savedStateHandle, repository, coroutineDispatcher)

        viewModel.signUp(validEmail, newPassword)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(
                email = validEmail,
                password = newPassword,
                isExistingEmail = true
            )
        )
    }

    @Test
    fun errorCreatingNewAccount() {
        val unavailableAuthRepository = InMemoryAuthRepository().apply {
            setUnavailable()
        }
        val viewModel = SignUpViewModel(
            savedStateHandle,
            unavailableAuthRepository,
            coroutineDispatcher
        )

        viewModel.signUp(validEmail, validPassword)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(
                email = validEmail,
                password = validPassword,
                isBackendError = true
            )
        )
    }

    @Test
    fun attemptToSignUpWhenOffline() {
        val offlineAuthRepository = InMemoryAuthRepository().apply {
            setOffline()
        }
        val viewModel = SignUpViewModel(
            savedStateHandle,
            offlineAuthRepository,
            coroutineDispatcher
        )

        viewModel.signUp(validEmail, validPassword)

        assertThat(viewModel.screenState.value).isEqualTo(
            SignUpScreenState(
                email = validEmail,
                password = validPassword,
                isOfflineError = true
            )
        )
    }

    private fun SignUpViewModel.signUp(email: String = "", password: String = "") {
        updateEmail(email)
        updatePassword(password)
        signUp()
    }
}