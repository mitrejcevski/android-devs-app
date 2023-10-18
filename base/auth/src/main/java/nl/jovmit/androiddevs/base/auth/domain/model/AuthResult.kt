package nl.jovmit.androiddevs.base.auth.domain.model

sealed class AuthResult {

    data class Success(
        val authToken: String,
        val user: User
    ) : AuthResult()

    data object Error : AuthResult()
}
