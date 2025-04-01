package nl.jovmit.androiddevs.core.network

import kotlinx.serialization.Serializable

@Serializable
data class SignUpData(
    val email: String,
    val password: String,
    val about: String
)