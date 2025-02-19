package nl.jovmit.androiddevs.domain.auth

import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.User
import java.util.UUID

class InMemoryAuthRepository(
    private val authToken: String = "",
    usersForPassword: Map<String, List<User>> = emptyMap()
) : AuthRepository {

    private var isUnavailable = false
    private var isOffline = false

    private val _usersForPassword = usersForPassword.toMutableMap()

    override suspend fun login(email: String, password: String): AuthResult {
        if (isUnavailable) return AuthResult.BackendError
        if (isOffline) return AuthResult.OfflineError
        val matchingUsers = _usersForPassword.getOrElse(password) { emptyList() }
        val found = matchingUsers.find { it.email == email }
        found?.let { user ->
            return AuthResult.Success(authToken, user)
        }
        return AuthResult.IncorrectCredentials
    }

    override suspend fun signUp(
        email: String,
        password: String,
        about: String
    ): AuthResult {
        if (isUnavailable) return AuthResult.BackendError
        if (isOffline) return AuthResult.OfflineError
        if (isKnownUser(email)) return AuthResult.ExistingUserError
        val user = User(UUID.randomUUID().toString(), email, about)
        saveUserData(password, user)
        return AuthResult.Success(authToken, user)
    }

    private fun isKnownUser(email: String) = _usersForPassword.values.flatten().any {
        it.email == email
    }

    private fun saveUserData(password: String, user: User) {
        val currentUsers = _usersForPassword.getOrElse(password) { emptyList() }
        currentUsers.toMutableList().apply {
            add(user)
        }
        _usersForPassword[password] = currentUsers
    }

    fun setLoggedInUsers(usersForPassword: Map<String, List<User>>) {
        _usersForPassword.putAll(usersForPassword)
    }

    fun setUnavailable() {
        isUnavailable = true
    }

    fun setOffline() {
        isOffline = true
    }
}