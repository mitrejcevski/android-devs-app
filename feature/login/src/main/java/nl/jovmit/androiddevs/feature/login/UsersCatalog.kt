package nl.jovmit.androiddevs.feature.login

interface UsersCatalog {
    suspend fun performLogin(email: String, password: String): User?
}