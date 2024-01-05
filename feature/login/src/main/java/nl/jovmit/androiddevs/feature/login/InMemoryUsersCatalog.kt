package nl.jovmit.androiddevs.feature.login

class InMemoryUsersCatalog(
    private val usersForPassword: Map<String, List<User>>
) : UsersCatalog {

    override fun performLogin(email: String, password: String): User? {
        return usersForPassword[password]?.find { it.email == email }
    }
}