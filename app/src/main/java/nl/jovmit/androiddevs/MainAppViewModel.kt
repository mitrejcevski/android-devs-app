package nl.jovmit.androiddevs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nl.jovmit.androiddevs.core.network.LogoutSignal
import javax.inject.Inject

@HiltViewModel
class MainAppViewModel @Inject constructor(
    private val loggedOutSignal: LogoutSignal
) : ViewModel() {

    private val _loggedOut = MutableSharedFlow<Unit>()
    val loggedOut = _loggedOut.asSharedFlow()

    fun observeLoggedOut() {
        loggedOutSignal.forcedLogout.onEach {
            _loggedOut.emit(Unit)
        }.launchIn(viewModelScope)
    }
}