package nl.jovmit.androiddevs.feature.login

data class LoginScreenState(
    val isLoading: Boolean = false,
    val email: String = "email",
    val password: String = "password",
    val didAuthorize: Boolean = false,
    val isAuthError: Boolean = false
)