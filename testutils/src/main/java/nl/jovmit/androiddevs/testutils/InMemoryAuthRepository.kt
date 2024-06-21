package nl.jovmit.androiddevs.testutils

import nl.jovmit.androiddevs.domain.auth.domain.model.AuthResult
import nl.jovmit.androiddevs.domain.auth.domain.model.User
import nl.jovmit.androiddevs.domain.auth.domain.repository.AuthRepository

class InMemoryAuthRepository : AuthRepository {

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