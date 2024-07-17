package nl.jovmit.androiddevs.base.auth

import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.User
import nl.jovmit.androiddevs.domain.auth.data.UserBuilder.Companion.aUser

class InMemoryAuthTest : AuthContractTest() {

    override fun usersCatalogWith(
        authToken: String,
        password: String,
        users: List<User>
    ): AuthRepository {
        return InMemoryAuthRepository(authToken, mapOf(password to users))
    }

    override fun usersCatalogWithoutPassword(password: String, users: List<User>): AuthRepository {
        return usersCatalogWith("", "anythingBut$password", users)
    }

    override fun usersCatalogWithoutEmail(password: String, email: String): AuthRepository {
        return usersCatalogWith(
            "",
            password,
            listOf(aUser().withEmail("anythingBut$email").build())
        )
    }
}