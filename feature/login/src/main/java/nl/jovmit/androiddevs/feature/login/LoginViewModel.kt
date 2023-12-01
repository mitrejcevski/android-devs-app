package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val screenState: StateFlow<LoginScreenState> =
        savedStateHandle.getStateFlow(LOGIN_SCREEN_STATE, LoginScreenState())

    fun updateEmail(newValue: String) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(email = newValue)
    }

    fun updatePassword(newValue: String) {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)
        savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(password = newValue)
    }

    fun login() {
        val value = savedStateHandle.get<LoginScreenState>(LOGIN_SCREEN_STATE)

        if (screenState.value.email == "valid@email.com") {
            savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(wrongCredentials = true)
        } else {
            val user = if (screenState.value.email == "bob@app.com") {
                User(email = "bob@app.com")
            } else {
                User(email = "alice@app.com")
            }
            savedStateHandle[LOGIN_SCREEN_STATE] = value?.copy(loggedInUser = user)
        }
    }

    companion object {
        const val LOGIN_SCREEN_STATE = "loginScreenStateKey"
    }
}
