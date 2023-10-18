package nl.jovmit.androiddevs.core.netowrk

import kotlinx.serialization.Serializable

@Serializable
data class SignUpData(
    val email: String,
    val password: String,
    val about: String
)