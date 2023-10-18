package nl.jovmit.androiddevs.core.netowrk

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val userData: UserData
) {

    @Serializable
    data class UserData(
        val id: String,
        val email: String,
        val about: String
    )
}