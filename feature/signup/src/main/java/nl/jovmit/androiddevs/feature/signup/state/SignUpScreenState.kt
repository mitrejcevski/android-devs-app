package nl.jovmit.androiddevs.feature.signup.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpScreenState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val about: String = "",
    val incorrectEmailFormat: Boolean = false,
    val incorrectPasswordFormat: Boolean = false,
    val isSignedUp: Boolean = false,
    val isExistingEmail: Boolean = false,
    val isBackendError: Boolean = false,
    val isOfflineError: Boolean = false
) : Parcelable