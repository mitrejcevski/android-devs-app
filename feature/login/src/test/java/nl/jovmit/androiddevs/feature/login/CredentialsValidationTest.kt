package nl.jovmit.androiddevs.feature.login

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.regex.Pattern

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
    fun incorrectEmail(email: String, expected: Boolean) {
        val emailValidator = EmailValidator()

        val result = emailValidator.validateEmail(email)

        assertThat(result).isEqualTo(expected)
    }

    class EmailValidator {

        fun validateEmail(email: String): Boolean {
            val regex = """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""
            val emailPattern = Pattern.compile(regex)
            return emailPattern.matcher(email).matches()
        }
    }
}