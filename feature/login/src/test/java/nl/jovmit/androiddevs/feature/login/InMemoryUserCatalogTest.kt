package nl.jovmit.androiddevs.feature.login

class InMemoryUserCatalogTest : UserCatalogContractTest() {

    override fun usersCatalogWith(password: String, users: List<User>): UsersCatalog {
        return InMemoryUsersCatalog(mapOf(password to users))
    }

    override fun usersCatalogWithoutPassword(password: String, users: List<User>): UsersCatalog {
        return usersCatalogWith("anythingBut$password", users)
    }

    override fun usersCatalogWithoutEmail(password: String, email: String): UsersCatalog {
        return usersCatalogWith(password, listOf(User("anythingBut$email")))
    }
}