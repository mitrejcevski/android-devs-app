package nl.jovmit.androiddevs.shared.ui.validation

class PasswordValidator {

    fun validatePassword(password: String): Boolean {
        val value = password.trim()
        return value.count() > 7 &&
                value.any { it.isUpperCase() } &&
                value.any { it.isDigit() }
    }
}