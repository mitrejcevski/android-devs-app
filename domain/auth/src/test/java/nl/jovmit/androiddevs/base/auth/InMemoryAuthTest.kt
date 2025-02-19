package nl.jovmit.androiddevs.base.auth

import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.User

class InMemoryAuthTest : AuthContractTest() {

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