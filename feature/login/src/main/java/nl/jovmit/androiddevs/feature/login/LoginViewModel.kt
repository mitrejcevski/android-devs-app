package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState = MutableStateFlow(LoginScreenState())
    val screenState: StateFlow<LoginScreenState> =
        savedStateHandle.getStateFlow(LOGIN_SCREEN_STATE, LoginScreenState())

    fun updateEmail(newValue: String) {
        _screenState.update { it.copy(email = newValue) }
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(email = newValue)
    }

    fun updatePassword(newValue: String) {
        _screenState.update { it.copy(password = newValue) }
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(password = newValue)
    }

    fun login() {
        TODO("Not yet implemented")
    }

    companion object {
        const val LOGIN_SCREEN_STATE = "loginScreenStateKey"
    }
}
