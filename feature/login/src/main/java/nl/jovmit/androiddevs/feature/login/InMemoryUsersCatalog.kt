package nl.jovmit.androiddevs.feature.login

class InMemoryUsersCatalog(
    private val knownUsers: List<User>
) {

    fun performLogin(email: String): User? {
        return knownUsers.find { it.email == email }
    }
}