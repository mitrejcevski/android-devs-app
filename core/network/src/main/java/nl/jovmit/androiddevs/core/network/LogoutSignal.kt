package nl.jovmit.androiddevs.core.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogoutSignal {

    private val _forcedLogout = MutableStateFlow<Unit?>(null)
    val forcedLogout: StateFlow<Unit?> = _forcedLogout.asStateFlow()

    fun onLoggedOut() {
        _forcedLogout.update { Unit }
    }
}