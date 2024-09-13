package nl.jovmit.androiddevs.feature.signup

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState

class SignUpViewModel {

    private val _screenState = MutableStateFlow(SignUpScreenState())
    val screenState = _screenState.asStateFlow()

    fun updateEmail(value: String) {
        _screenState.update { it.copy(email = value) }
    }

    fun updatePassword(value: String) {
        _screenState.update { it.copy(password = value) }
    }
}