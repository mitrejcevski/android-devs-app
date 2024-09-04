package nl.jovmit.androiddevs.domain.auth

import nl.jovmit.androiddevs.core.network.AuthService
import nl.jovmit.androiddevs.core.network.LoginData
import nl.jovmit.androiddevs.core.network.SignUpData
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.toDomain
import javax.inject.Inject

internal class RemoteAuthRepository @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        val loginData = LoginData(email, password)
        return try {
            val response = authService.login(loginData)
            AuthResult.Success(response.token, response.userData.toDomain())
        } catch (exception: Exception) {
            AuthResult.Error
        }
    }

    override suspend fun signUp(email: String, password: String, about: String): AuthResult {
       TODO()
    }
}