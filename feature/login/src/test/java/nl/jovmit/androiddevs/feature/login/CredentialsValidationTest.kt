package nl.jovmit.androiddevs.feature.login

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CredentialsValidationTest {

    @ParameterizedTest
    @CsvSource(
        "'   ', false",
        "' a  ', false",
        "' ab  ', false",
        "'ab@c', false",
        "'abc@d', false",
        "'abc@de.f', false",
        "'abc@de.fe', true",
    )
    fun emailValidation(email: String, expected: Boolean) {
        val emailValidator = EmailValidator()

        val result = emailValidator.validateEmail(email)

        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        "'   ', false",
        "' a  ', false",
        "'ab  ', false",
        "'short_1', false",
        "'password', false",
        "'pAssW0rd', true",
        "'abc@De.f1', true",
    )
    fun passwordValidation(password: String, expected: Boolean) {
        val passwordValidator = PasswordValidator()

        val result = passwordValidator.validatePassword(password)

        assertThat(result).isEqualTo(expected)
    }
}