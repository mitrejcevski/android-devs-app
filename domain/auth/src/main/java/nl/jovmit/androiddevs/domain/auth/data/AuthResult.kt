package nl.jovmit.androiddevs.domain.auth.data

sealed class AuthResult {

    data class Success(
        val authToken: String,
        val user: User
    ) : AuthResult()

    data object Error : AuthResult()
}
