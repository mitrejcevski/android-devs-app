package nl.jovmit.androiddevs.feature.login.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val email: String
)
