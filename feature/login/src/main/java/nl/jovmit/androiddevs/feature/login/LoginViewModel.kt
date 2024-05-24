package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val usersCatalog: UsersCatalog,
    private val background: CoroutineDispatcher
) : ViewModel() {

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()

    val screenState: StateFlow<LoginScreenState> =
        savedStateHandle.getStateFlow(LOGIN_SCREEN_STATE, LoginScreenState())

    fun updateEmail(newValue: String) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(
            email = newValue,
            isWrongEmailFormat = false
        )
    }

    fun updatePassword(newValue: String) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(
            password = newValue,
            isBadPasswordFormat = false
        )
    }

    fun login() {
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
            val found = withContext(background) {
                usersCatalog.performLogin(email, password)
            }
            onLoginResults(found)
        }
    }

    private fun onLoginResults(found: User?) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        if (found != null) {
            savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(loggedInUser = found)
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
