package nl.jovmit.androiddevs.feature.signup

import androidx.lifecycle.SavedStateHandle
import nl.jovmit.androiddevs.core.view.extensions.update
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState

class SignUpViewModel(
    private val savedStateHandle: SavedStateHandle
) {

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

    companion object {
        private const val SIGN_UP = "signUpKey"
    }
}