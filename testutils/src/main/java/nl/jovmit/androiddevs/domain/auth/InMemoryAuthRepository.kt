package nl.jovmit.androiddevs.domain.auth

import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.User

class InMemoryAuthRepository(
    private val authToken: String = "",
    usersForPassword: Map<String, List<User>>
) : AuthRepository {

    private val _usersForPassword = usersForPassword.toMutableMap()

    override suspend fun login(email: String, password: String): AuthResult {
        val matchingUsers = _usersForPassword.getOrElse(password) { emptyList() }
        val found = matchingUsers.find { it.email == email }
        found?.let { user ->
            return AuthResult.Success(authToken, user)
        }
        return AuthResult.Error
    }

    override suspend fun signUp(email: String, password: String, about: String): AuthResult {
        TODO("Not yet implemented")
    }
}