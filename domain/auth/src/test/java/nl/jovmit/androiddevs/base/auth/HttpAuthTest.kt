package nl.jovmit.androiddevs.base.auth

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.jovmit.androiddevs.core.network.AuthResponse
import nl.jovmit.androiddevs.core.network.AuthService
import nl.jovmit.androiddevs.core.network.LoginData
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.RemoteAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.auth.data.UserBuilder.Companion.aUser
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.Retrofit

class HttpAuthTest : AuthContractTest() {

    private val mockWebServer = MockWebServer()
    private val retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(Json.Default.asConverterFactory("application/json".toMediaType()))
        .build()
    private val authorizationApi = retrofit.create(AuthService::class.java)

    override fun usersCatalogWith(
        authToken: String,
        password: String,
        users: List<User>
    ): AuthRepository {
        mockWebServer.dispatcher = CustomDispatcher(authToken, password, users)
        return RemoteAuthRepository(authorizationApi)
    }

    override fun usersCatalogWithoutPassword(password: String, users: List<User>): AuthRepository {
        return usersCatalogWith("", "different$password", users)
    }

    override fun usersCatalogWithoutEmail(password: String, email: String): AuthRepository {
        return usersCatalogWith("", password, listOf(aUser().withEmail("different$email").build()))
    }

    private class CustomDispatcher(
        private val authToken: String,
        private val password: String,
        private val users: List<User>
    ) : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            val requestBody = request.body.readUtf8()
            val loginData = Json.Default.decodeFromString<LoginData>(requestBody)
            return if (loginData.password == password) {
                val matchingUser = users.find { it.email == loginData.email }
                matchingUser?.let { userResponse(matchingUser) } ?: invalidCredentials()
            } else {
                invalidCredentials()
            }
        }

        private fun userResponse(matchingUser: User): MockResponse {
            val entity = Json.encodeToString(matchingUser.toResponseEntity())
            return MockResponse()
                .setResponseCode(200)
                .setBody("""{"token":"$authToken", "userData": $entity}""")
        }

        private fun User.toResponseEntity(): AuthResponse.UserData {
            return AuthResponse.UserData(userId, email, about)
        }

        private fun invalidCredentials(): MockResponse {
            return MockResponse()
                .setResponseCode(401)
                .setBody("""{"error":"Invalid credentials"}""")
        }
    }
}