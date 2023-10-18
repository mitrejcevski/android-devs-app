package nl.jovmit.androiddevs.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jovmit.androiddevs.base.auth.domain.model.AuthResult
import nl.jovmit.androiddevs.base.auth.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val backgroundDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _screenState = MutableStateFlow(LoginScreenState())
    val screenState = _screenState.asStateFlow()

    fun updateEmail(value: String) {
        _screenState.update { it.copy(email = value) }
    }

    fun updatePassword(value: String) {
        _screenState.update { it.copy(password = value) }
    }

    fun clearAuthError() {
        _screenState.update { it.copy(isAuthError = false) }
    }

    fun login() {
        viewModelScope.launch {
            setLoading()
            val authResult = withContext(backgroundDispatcher) {
                val email = screenState.value.email
                val password = screenState.value.password
                authRepository.login(email, password)
            }
            onAuthResult(authResult)
        }
    }

    private fun onAuthResult(authResult: AuthResult) {
        when (authResult) {
            is AuthResult.Success -> {
                _screenState.update { it.copy(isLoading = false, didAuthorize = true) }
            }

            is AuthResult.Error -> {
                _screenState.update { it.copy(isLoading = false, isAuthError = true) }
            }
        }
    }

    private fun setLoading() {
        _screenState.update { it.copy(isLoading = true) }
    }
}