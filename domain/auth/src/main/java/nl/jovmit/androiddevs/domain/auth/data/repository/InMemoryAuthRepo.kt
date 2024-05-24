package nl.jovmit.androiddevs.domain.auth.data.repository

import nl.jovmit.androiddevs.domain.auth.domain.model.AuthResult
import nl.jovmit.androiddevs.domain.auth.domain.model.User
import nl.jovmit.androiddevs.domain.auth.domain.repository.AuthRepository
import javax.inject.Inject

class InMemoryAuthRepo @Inject constructor() : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        if (email == "email" && password == "password") {
            return AuthResult.Success("token", User("uid", email, "about"))
        }
        return AuthResult.Error
    }

    override suspend fun signUp(email: String, password: String, about: String): AuthResult {
        TODO("Not yet implemented")
    }
}