package nl.jovmit.androiddevs.domain.auth.data

sealed class AuthResult {

    data class Success(
        val authToken: String,
        val user: User
    ) : AuthResult()

    data object BackendError : AuthResult()

    data object IncorrectCredentials: AuthResult()

    data object ExistingUserError : AuthResult()

    data object OfflineError : AuthResult()
}
