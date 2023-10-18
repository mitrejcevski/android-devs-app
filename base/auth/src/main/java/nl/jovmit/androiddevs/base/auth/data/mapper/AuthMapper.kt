package nl.jovmit.androiddevs.base.auth.data.mapper

import nl.jovmit.androiddevs.base.auth.domain.model.User
import nl.jovmit.androiddevs.core.netowrk.AuthResponse

fun AuthResponse.UserData.toDomain(): User {
    return User(
        userId = id,
        email = email,
        about = about
    )
}