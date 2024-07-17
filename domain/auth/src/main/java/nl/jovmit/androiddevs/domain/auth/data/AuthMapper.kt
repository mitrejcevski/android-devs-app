package nl.jovmit.androiddevs.domain.auth.data

import nl.jovmit.androiddevs.core.network.AuthResponse

fun AuthResponse.UserData.toDomain(): User {
    return User(
        userId = id,
        email = email,
        about = about
    )
}