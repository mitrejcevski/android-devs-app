package nl.jovmit.androiddevs.feature.login

data class LoginScreenState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val didAuthorize: Boolean = false,
    val isAuthError: Boolean = false
)