package nl.jovmit.androiddevs.core.network.auth

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.DslPart
import au.com.dius.pact.consumer.dsl.LambdaDsl
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.dsl.newObject
import au.com.dius.pact.consumer.junit5.PactConsumerTest
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import nl.jovmit.androiddevs.core.network.AuthResponse
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.core5.http.ContentType
import org.junit.jupiter.api.Test
import java.util.UUID

@PactConsumerTest
class SignUpContractTest {

  private val expectedAuthResponse = AuthResponse(
    token = UUID.randomUUID().toString(),
    userData = AuthResponse.UserData(
      id = UUID.randomUUID().toString(),
      email = "email@email.com",
      about = "about this user"
    )
  )

  @Pact(provider = "ApiProvider", consumer = "MobileApp")
  fun createPact(builder: PactDslWithProvider): V4Pact {
    return builder.uponReceiving("SignUpCall")
      .path("/signUp")
      .method("POST")
      .matchHeader("accept", "application/json")
      .body(createSignUpBody("user", "pass"))
      .willRespondWith()
      .matchHeader("content-type", "application/json")
      .body(createAuthResponse())
      .status(200)
      .toPact(V4Pact::class.java)
  }

  @Test
  @PactTestFor("ApiProvider")
  fun performSignUpCall(mockServer: MockServer) {
    val httpResponse = Request.post(mockServer.getUrl() + "/signUp")
      .addHeader("accept", "application/json")
      .bodyString("""{"username":"user", "password":"pass"}""", ContentType.APPLICATION_JSON)
      .execute()
    val content = httpResponse.returnContent().asString()
    val authResponse = Json.decodeFromString<AuthResponse>(content)
    assertThat(authResponse).isEqualTo(expectedAuthResponse)
  }

  private fun createAuthResponse(): DslPart = LambdaDsl.newJsonBody { body ->
    body.stringType("token", expectedAuthResponse.token)
    body.newObject("userData") {
      stringType("id", expectedAuthResponse.userData.id)
      stringType("email", expectedAuthResponse.userData.email)
      stringType("about", expectedAuthResponse.userData.about)
    }
  }.build()

  private fun createSignUpBody(
    username: String,
    password: String
  ): DslPart = LambdaDsl.newJsonBody { body ->
      body.stringType("username", username)
      body.stringType("password", password)
  }.build()
}