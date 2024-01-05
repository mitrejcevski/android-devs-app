package nl.jovmit.androiddevs.feature.login

interface UsersCatalog {
    fun performLogin(email: String, password: String): User?
}