package nl.jovmit.androiddevs.feature.timeline

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.jovmit.androiddevs.core.network.LogoutSignal
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    private val logoutSignal: LogoutSignal
) : ViewModel() {

    fun doLogout() {
        logoutSignal.onLoggedOut()
    }
}