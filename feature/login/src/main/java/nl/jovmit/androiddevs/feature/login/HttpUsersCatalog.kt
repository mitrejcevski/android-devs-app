package nl.jovmit.androiddevs.feature.login

import nl.jovmit.androiddevs.feature.login.data.LoginData
import retrofit2.HttpException

class HttpUsersCatalog(
    private val authApi: AuthorizationApi
) : UsersCatalog {

    override suspend fun performLogin(email: String, password: String): User? {
        return try {
            val response = authApi.login(LoginData(email, password))
            User(response.email)
        } catch (httpException: HttpException) {
            null
        }
    }
}