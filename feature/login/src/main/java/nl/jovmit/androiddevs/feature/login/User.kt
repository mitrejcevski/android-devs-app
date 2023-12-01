package nl.jovmit.androiddevs.feature.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val email: String): Parcelable