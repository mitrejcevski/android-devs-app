package nl.jovmit.androiddevs.feature.login

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

abstract class UserCatalogContractTest {

    private val bobPassword = ":irrelevant:"
    private val bob = User(":irrelevant_too:")

    @Test
    fun successfulLogin() = runTest {
        val usersCatalog = usersCatalogWith(bobPassword, listOf(bob))

        val result = usersCatalog.performLogin(bob.email, bobPassword)

        assertThat(result).isEqualTo(bob)
    }

    @Test
    fun wrongPassword() = runTest {
        val usersCatalog = usersCatalogWithoutPassword(bobPassword, listOf(bob))

        val result = usersCatalog.performLogin(bob.email, bobPassword)

        assertThat(result).isNull()
    }

    @Test
    fun wrongEmail() = runTest {
        val usersCatalog = usersCatalogWithoutEmail(bobPassword, bob.email)

        val result = usersCatalog.performLogin(bob.email, bobPassword)

        assertThat(result).isNull()
    }

    abstract fun usersCatalogWith(password: String, users: List<User>): UsersCatalog

    abstract fun usersCatalogWithoutPassword(password: String, users: List<User>): UsersCatalog

    abstract fun usersCatalogWithoutEmail(password: String, email: String): UsersCatalog
}