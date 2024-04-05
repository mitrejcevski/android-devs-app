package nl.jovmit.androiddevs.feature.login.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val email: String,
    val password: String
)
