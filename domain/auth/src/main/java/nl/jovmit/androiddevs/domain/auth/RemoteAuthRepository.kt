package nl.jovmit.androiddevs.domain.auth

import nl.jovmit.androiddevs.core.network.AuthResponse
import nl.jovmit.androiddevs.core.network.AuthService
import nl.jovmit.androiddevs.core.network.LoginData
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.User
import retrofit2.HttpException
import java.io.IOException

class RemoteAuthRepository(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            val response = authService.login(LoginData(email, password))
            val user = response.userData.toDomain()
            AuthResult.Success(response.token, user)
        } catch (httpException: HttpException) {
            backendErrorFor(httpException)
        } catch (offline: IOException) {
            AuthResult.OfflineError
        }
    }

    private fun backendErrorFor(httpException: HttpException): AuthResult {
        return if (httpException.code() == 401) {
            AuthResult.IncorrectCredentials
        } else {
            AuthResult.BackendError
        }
    }

    private fun AuthResponse.UserData.toDomain(): User {
        return User(
            userId = id,
            email = email,
            about = about
        )
    }

    override suspend fun signUp(email: String, password: String, about: String): AuthResult {
        TODO("Not yet implemented")
    }
}