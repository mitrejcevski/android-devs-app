package nl.jovmit.androiddevs.feature.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val loggedInUser: User? = null,
    val wrongCredentials: Boolean = false,
    val isWrongEmailFormat: Boolean = false,
    val isBadPasswordFormat: Boolean = false
) : Parcelable
