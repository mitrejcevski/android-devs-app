package nl.jovmit.androiddevs.feature.signup.state

data class SignUpScreenState(
    val email: String = "",
    val password: String = "",
    val about: String = ""
)