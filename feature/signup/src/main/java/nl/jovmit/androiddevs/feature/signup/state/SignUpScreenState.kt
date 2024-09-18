package nl.jovmit.androiddevs.feature.signup.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val about: String = ""
) : Parcelable