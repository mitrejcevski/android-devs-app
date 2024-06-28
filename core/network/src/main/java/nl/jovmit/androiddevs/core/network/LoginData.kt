package nl.jovmit.androiddevs.core.network

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val email: String,
    val password: String
)