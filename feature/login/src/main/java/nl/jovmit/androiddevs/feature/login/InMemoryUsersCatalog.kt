package nl.jovmit.androiddevs.feature.login

class InMemoryUsersCatalog(
    usersForPassword: Map<String, List<User>>
) : UsersCatalog {

    private val _usersForPassword = usersForPassword.toMutableMap()

    override suspend fun performLogin(email: String, password: String): User? {
        return _usersForPassword[password]?.find { it.email == email }
    }

    fun setLoggedInUsers(usersForPassword: Map<String, List<User>>) {
        _usersForPassword.clear()
        _usersForPassword.putAll(usersForPassword)
    }
}