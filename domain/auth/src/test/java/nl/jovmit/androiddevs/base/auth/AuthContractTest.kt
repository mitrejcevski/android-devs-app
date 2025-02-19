package nl.jovmit.androiddevs.base.auth

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.auth.data.UserBuilder.Companion.aUser
import org.junit.jupiter.api.Test
import java.util.UUID

abstract class AuthContractTest {

    private val authToken = UUID.randomUUID().toString()
    private val bobPassword = ":irrelevant:"
    private val bob = aUser().withEmail(":irrelevant_too:").build()

    @Test
    fun successfulLogin() = runTest {
        val usersForPassword = mapOf(bobPassword to listOf(bob))
        val repository = authRepositoryWith(authToken, usersForPassword)

        val result = repository.login(bob.email, bobPassword)

        assertThat(result).isEqualTo(AuthResult.Success(authToken, bob))
    }

    @Test
    fun attemptToLoginWithWrongPassword() = runTest {
        val usersForPassword = mapOf(bobPassword to listOf(bob))
        val repository = authRepositoryWith(authToken, usersForPassword)

        val result = repository.login(bob.email, "otherThan$bobPassword")

        assertThat(result).isEqualTo(AuthResult.IncorrectCredentials)
    }

    @Test
    fun attemptToLoginWithWrongEmail() = runTest {
        val usersForPassword = mapOf(bobPassword to listOf(bob))
        val repository = authRepositoryWith(authToken, usersForPassword)

        val result = repository.login("anythingBut${bob.email}", bobPassword)

        assertThat(result).isEqualTo(AuthResult.IncorrectCredentials)
    }

    @Test
    fun backendErrorWhenLoggingIn() = runTest {
        val repository = unavailableAuthRepository()

        val result = repository.login(":irrelevant:", ":irrelevant")

        assertThat(result).isEqualTo(AuthResult.BackendError)
    }

    @Test
    fun offlineErrorWhenLoggingIn() = runTest {
        val repository = offlineAuthRepository()

        val result = repository.login(":irrelevant:", ":irrelevant")

        assertThat(result).isEqualTo(AuthResult.OfflineError)
    }

    abstract fun authRepositoryWith(
        authToken: String,
        usersForPassword: Map<String, List<User>>
    ): AuthRepository

    abstract fun unavailableAuthRepository(): AuthRepository

    abstract fun offlineAuthRepository(): AuthRepository
}