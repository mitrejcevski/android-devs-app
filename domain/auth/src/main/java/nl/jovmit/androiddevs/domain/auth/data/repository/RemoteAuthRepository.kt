package nl.jovmit.androiddevs.domain.auth.data.repository

import nl.jovmit.androiddevs.domain.auth.data.mapper.toDomain
import nl.jovmit.androiddevs.domain.auth.domain.model.AuthResult
import nl.jovmit.androiddevs.domain.auth.domain.repository.AuthRepository
import nl.jovmit.androiddevs.core.netowrk.AuthService
import nl.jovmit.androiddevs.core.netowrk.LoginData
import nl.jovmit.androiddevs.core.netowrk.SignUpData
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
        val signUpData = SignUpData(email, password, about)
        return try {
            val response = authService.signUp(signUpData)
            AuthResult.Success(response.token, response.userData.toDomain())
        } catch (exception: Exception) {
            AuthResult.Error
        }
    }
}