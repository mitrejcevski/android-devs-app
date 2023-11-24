package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _screenState = MutableStateFlow(LoginScreenState())
    val screenState: StateFlow<LoginScreenState> = _screenState.asStateFlow()

    fun updateEmail(newValue: String) {
        TODO("Not yet implemented")
    }

    fun updatePassword(newValue: String) {
        TODO("Not yet implemented")
    }

    fun login() {
        TODO("Not yet implemented")
    }
}
