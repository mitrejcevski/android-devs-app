package nl.jovmit.androiddevs.feature.welcome

import androidx.lifecycle.ViewModel
import nl.jovmit.androiddevs.domain.auth.domain.repository.AuthRepository

class WelcomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun load() {

    }
}