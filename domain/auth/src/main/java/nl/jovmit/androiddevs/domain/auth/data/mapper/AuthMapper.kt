package nl.jovmit.androiddevs.domain.auth.data.mapper

import nl.jovmit.androiddevs.domain.auth.domain.model.User
import nl.jovmit.androiddevs.core.netowrk.AuthResponse

fun AuthResponse.UserData.toDomain(): User {
    return User(
        userId = id,
        email = email,
        about = about
    )
}