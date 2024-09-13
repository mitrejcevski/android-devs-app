package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
    private val background: CoroutineDispatcher
) : ViewModel(), LoginActions {

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()

    val screenState: StateFlow<LoginScreenState> =
        savedStateHandle.getStateFlow(LOGIN_SCREEN_STATE, LoginScreenState())

    override fun updateEmail(newValue: String) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(
            email = newValue,
            isWrongEmailFormat = false
        )
    }

    override fun updatePassword(newValue: String) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(
            password = newValue,
            isBadPasswordFormat = false
        )
    }

    override fun login() {
        val email = screenState.value.email
        val password = screenState.value.password
        if (!emailValidator.validateEmail(email)) {
            setIncorrectEmailFormatError()
        } else if (!passwordValidator.validatePassword(password)) {
            setIncorrectPasswordFormat()
        } else {
            proceedLoggingIn(email, password)
        }
    }

    private fun proceedLoggingIn(email: String, password: String) {
        viewModelScope.launch {
            val loginResult = withContext(background) {
                authRepository.login(email, password)
            }
            onLoginResults(loginResult)
        }
    }

    private fun onLoginResults(loginResult: AuthResult) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        if (loginResult is AuthResult.Success) {
            savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(loggedInUser = loginResult.user.email)
        } else {
            savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(wrongCredentials = true)
        }
    }

    private fun setIncorrectEmailFormatError() {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(isWrongEmailFormat = true)
    }

    private fun setIncorrectPasswordFormat() {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(isBadPasswordFormat = true)
    }

    companion object {
        const val LOGIN_SCREEN_STATE = "loginScreenStateKey"
    }
}
