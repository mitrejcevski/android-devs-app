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
        val usersCatalog = usersCatalogWith(authToken, bobPassword, listOf(bob))

        val result = usersCatalog.login(bob.email, bobPassword)

        assertThat(result).isEqualTo(AuthResult.Success(authToken, bob))
    }

    @Test
    fun wrongPassword() = runTest {
        val usersCatalog = usersCatalogWithoutPassword(bobPassword, listOf(bob))

        val result = usersCatalog.login(bob.email, bobPassword)

        assertThat(result).isEqualTo(AuthResult.ExistingUserError)
    }

    @Test
    fun wrongEmail() = runTest {
        val usersCatalog = usersCatalogWithoutEmail(bobPassword, bob.email)

        val result = usersCatalog.login(bob.email, bobPassword)

        assertThat(result).isEqualTo(AuthResult.ExistingUserError)
    }

    abstract fun usersCatalogWith(authToken: String, password: String, users: List<User>): AuthRepository

    abstract fun usersCatalogWithoutPassword(password: String, users: List<User>): AuthRepository

    abstract fun usersCatalogWithoutEmail(password: String, email: String): AuthRepository
}