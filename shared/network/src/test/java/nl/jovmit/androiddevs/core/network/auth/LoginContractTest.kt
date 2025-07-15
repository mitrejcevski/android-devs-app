package nl.jovmit.androiddevs.core.network.auth

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.dsl.newObject
import au.com.dius.pact.consumer.junit5.PactConsumerTest
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import nl.jovmit.androiddevs.core.network.AuthResponse
import nl.jovmit.androiddevs.core.network.LoginData
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.core5.http.ContentType
import org.junit.jupiter.api.Test
import java.util.UUID

@PactConsumerTest
class LoginContractTest {

  private val loginData = LoginData(
    email = "email@email.com",
    password = "my password"
  )

  private val loginDataJson = newJsonBody { body ->
    body.stringType("email", loginData.email)
    body.stringType("password", loginData.password)
  }.build()

  private val authResponse = AuthResponse(
    token = UUID.randomUUID().toString(),
    userData = AuthResponse.UserData(
      id = UUID.randomUUID().toString(),
      email = loginData.email,
      about = ""
    )
  )

  private val authResponseJson = newJsonBody { body ->
    body.stringType("token", authResponse.token)
    body.newObject("userData") {
      stringType("id", authResponse.userData.id)
      stringType("email", authResponse.userData.email)
      stringType("about", authResponse.userData.about)
    }
  }.build()

  @Pact(provider = "ApiProvider", consumer = "MobileApp")
  fun createPact(builder: PactDslWithProvider): V4Pact {
    return builder.uponReceiving("Login Call")
      .path("/auth/login")
      .method("POST")
      .matchHeader("accept", "application/json")
      .body(loginDataJson)
      .willRespondWith()
      .matchHeader("content-type", "application/json")
      .body(authResponseJson)
      .status(200)
      .toPact(V4Pact::class.java)
  }

  @Test
  @PactTestFor("ApiProvider")
  fun performLoginCall(mockServer: MockServer) {
    val httpResponse = Request.post(mockServer.getUrl() + "/auth/login")
      .addHeader("accept", "application/json")
      .bodyString(Json.encodeToString(loginData), ContentType.APPLICATION_JSON)
      .execute()
    val content = httpResponse.returnContent().asString()
    val authResponse = Json.decodeFromString<AuthResponse>(content)
    assertThat(authResponse).isNotNull()
  }
}