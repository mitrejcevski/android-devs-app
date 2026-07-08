package nl.jovmit.androiddevs.base.auth

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.AuthResult
import nl.jovmit.androiddevs.domain.auth.data.User
import org.junit.jupiter.api.Test

class InMemoryAuthTest : AuthContractTest() {

    @Test
    fun signedUpUsersHaveDistinctIdentities() = runTest {
        val repository = InMemoryAuthRepository()

        val alice = repository.signUp("alice@androiddevs.nl", "passWord12.", "Compose mentor")
        val bob = repository.signUp("bob@androiddevs.nl", "passWord12.", "Architecture coach")

        assertThat((alice as AuthResult.Success).user.userId)
            .isNotEqualTo((bob as AuthResult.Success).user.userId)
    }

    @Test
    fun signedUpUsersCanLogInWithTheirCredentials() = runTest {
        val repository = InMemoryAuthRepository()
        val signedUp = repository.signUp("alice@androiddevs.nl", "passWord12.", "Compose mentor")

        val loggedIn = repository.login("alice@androiddevs.nl", "passWord12.")

        assertThat(loggedIn).isEqualTo(signedUp)
    }

    override fun authRepositoryWith(
        authToken: String,
        usersForPassword: Map<String, List<User>>
    ): AuthRepository {
        return InMemoryAuthRepository(authToken, usersForPassword)
    }

    override fun unavailableAuthRepository(): AuthRepository {
        return InMemoryAuthRepository().apply { setUnavailable() }
    }

    override fun offlineAuthRepository(): AuthRepository {
        return InMemoryAuthRepository().apply { setOffline() }
    }
}
