package nl.jovmit.androiddevs.base.auth

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.jovmit.androiddevs.core.network.AuthResponse
import nl.jovmit.androiddevs.core.network.AuthService
import nl.jovmit.androiddevs.core.network.LoginData
import nl.jovmit.androiddevs.core.network.SignUpData
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.RemoteAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class HttpAuthTest : AuthContractTest() {

    private val mockWebServer = MockWebServer()
    private val retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(Json.Default.asConverterFactory("application/json".toMediaType()))
        .build()

    private val authService = retrofit.create(AuthService::class.java)

    override fun authRepositoryWith(
        authToken: String,
        usersForPassword: Map<String, List<User>>
    ): AuthRepository {
        mockWebServer.dispatcher = CustomDispatcher(
            authToken = authToken,
            usersForPassword = usersForPassword
        )
        return RemoteAuthRepository(authService)
    }

    override fun unavailableAuthRepository(): AuthRepository {
        val unavailableApi = object : AuthService {
            override suspend fun signUp(signUpData: SignUpData): AuthResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginData: LoginData): AuthResponse {
                throw HttpException(Response.error<String>(400, "".toResponseBody()))
            }
        }
        return RemoteAuthRepository(unavailableApi)
    }

    override fun offlineAuthRepository(): AuthRepository {
        val offline = object : AuthService {
            override suspend fun signUp(signUpData: SignUpData): AuthResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginData: LoginData): AuthResponse {
                throw IOException()
            }
        }
        return RemoteAuthRepository(offline)
    }

    private class CustomDispatcher(
        private val authToken: String,
        private val usersForPassword: Map<String, List<User>>
    ) : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            val requestBody = request.body.readUtf8()
            val loginData = Json.Default.decodeFromString<LoginData>(requestBody)
            if (usersForPassword.keys.contains(loginData.password)) {
                val matchingUsers = usersForPassword[loginData.password]
                val user = matchingUsers?.find { it.email == loginData.email }
                return user?.let { userResponse(authToken, it) } ?: invalidCredentials()
            } else {
                return invalidCredentials()
            }
        }

        private fun userResponse(authToken: String, user: User): MockResponse {
            val response = AuthResponse(
                token = authToken,
                userData = AuthResponse.UserData(
                    id = user.userId,
                    email = user.email,
                    about = user.about
                )
            )
            val entity = Json.encodeToString(response)
            return MockResponse().setResponseCode(200)
                .setBody(entity)
        }

        private fun invalidCredentials() = MockResponse().setResponseCode(401)
            .setBody("""{"error":"Invalid Credentials"}""")
    }
}
