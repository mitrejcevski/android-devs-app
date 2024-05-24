package nl.jovmit.androiddevs.feature.login

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.jovmit.androiddevs.feature.login.data.LoginData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.Retrofit

class HttpUsersCatalogTest : UserCatalogContractTest() {

    private val mockWebServer = MockWebServer()
    private val retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
    private val authorizationApi = retrofit.create(AuthorizationApi::class.java)

    override fun usersCatalogWith(password: String, users: List<User>): UsersCatalog {
        mockWebServer.dispatcher = CustomDispatcher(password, users)
        return HttpUsersCatalog(authorizationApi)
    }

    override fun usersCatalogWithoutPassword(password: String, users: List<User>): UsersCatalog {
        return usersCatalogWith("different$password", users)
    }

    override fun usersCatalogWithoutEmail(password: String, email: String): UsersCatalog {
        return usersCatalogWith(password, listOf(User("different$email")))
    }

    private class CustomDispatcher(
        private val password: String,
        private val users: List<User>
    ) : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            val requestBody = request.body.readUtf8()
            val loginData = Json.decodeFromString<LoginData>(requestBody)
            return if (loginData.password == password) {
                val matchingUser = users.find { it.email == loginData.email }
                matchingUser?.let { userResponse(matchingUser) } ?: invalidCredentials()
            } else {
                invalidCredentials()
            }
        }

        private fun userResponse(matchingUser: User) = MockResponse()
            .setResponseCode(200)
            .setBody("""{"email":"${matchingUser.email}"}""")

        private fun invalidCredentials(): MockResponse {
            return MockResponse()
                .setResponseCode(401)
                .setBody("""{"error":"Invalid credentials"}""")
        }
    }
}