package nl.jovmit.androiddevs.core.netowrk

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val email: String,
    val password: String
)