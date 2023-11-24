package nl.jovmit.androiddevs.feature.login

import android.os.Parcelable

@Parcelize
data class LoginScreenState(
    val email: String = "",
    val password: String = ""
): Parcelable