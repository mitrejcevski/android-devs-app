package nl.jovmit.androiddevs.feature.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.jovmit.androiddevs.core.view.extensions.update
import nl.jovmit.androiddevs.core.view.validation.EmailValidator
import nl.jovmit.androiddevs.core.view.validation.PasswordValidator
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState

class SignUpViewModel (
    private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()

    val screenState = savedStateHandle.getStateFlow(SIGN_UP, SignUpScreenState())

    fun updateEmail(value: String) {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(email = value)
        }
    }

    fun updatePassword(value: String) {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(password = value)
        }
    }

    fun updateAbout(value: String) {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(about = value)
        }
    }

    fun signUp() {
        val email = screenState.value.email
        val password = screenState.value.password
        val about = screenState.value.about

        val isEmailValid = emailValidator.validateEmail(email)
        val isPasswordValid = passwordValidator.validatePassword(password)

        if (!isEmailValid) { setIncorrectEmailFormatError() }
        if (!isPasswordValid) { setIncorrectPasswordFormatError() }

        if (isEmailValid && isPasswordValid) {
            performSignUp(email, password, about)
        }
    }

    private fun setIncorrectEmailFormatError() {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(incorrectEmailFormat = true)
        }
    }

    private fun setIncorrectPasswordFormatError() {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(incorrectPasswordFormat = true)
        }
    }

    private fun performSignUp(email: String, password: String, about: String) {
        viewModelScope.launch { //TODO (we need to offload from main thread)
            val result = authRepository.signUp(email, password, about)
            onAuthResults(result)
        }
    }

    private fun onAuthResults(result: AuthResult) {
        when (result) {
            is AuthResult.Success -> onSignedUp()
            is AuthResult.BackendError -> onBackendError()
            is AuthResult.ExistingUserError -> onExistingUserError()
            is AuthResult.OfflineError -> onOfflineError()
        }
    }

    private fun onSignedUp() {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(isSignedUp = true)
        }
    }

    private fun onBackendError() {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(isBackendError = true)
        }
    }

    private fun onExistingUserError() {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(isExistingEmail = true)
        }
    }

    private fun onOfflineError() {
        savedStateHandle.update<SignUpScreenState>(SIGN_UP) {
            it.copy(isOfflineError = true)
        }
    }

    companion object {
        private const val SIGN_UP = "signUpKey"
    }
}