package nl.jovmit.androiddevs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import nl.jovmit.androiddevs.core.view.R

fun launchLoginScreen(
    rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: LoginRobot.() -> Unit
): LoginRobot {
    rule.onNodeWithText("Login").performClick()
    return LoginRobot(rule).apply(block)
}

class LoginRobot(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun typeEmail(email: String) {
        rule.onNodeWithTag("email")
            .performTextInput(email)
    }

    fun typePassword(password: String) {
        rule.onNodeWithTag("password")
            .performTextInput(password)
    }

    fun submit() {
        rule.onNodeWithTag("loginButton")
            .performClick()
    }

    infix fun verify(
        block: LoginVerification.() -> Unit
    ): LoginVerification {
        return LoginVerification(rule).apply(block)
    }
}

class LoginVerification(
    private val rule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun badEmailErrorIsShown() {
        val badEmailErrorMessage = rule.activity.getString(R.string.error_bad_email_format)
        rule.onNodeWithText(badEmailErrorMessage)
            .assertIsDisplayed()
    }

    fun badEmailErrorIsGone() {
        val badEmailErrorMessage = rule.activity.getString(R.string.error_bad_email_format)
        rule.onNodeWithText(badEmailErrorMessage)
            .assertDoesNotExist()
    }

    fun badPasswordErrorIsShown() {
        val badPasswordErrorMessage = rule.activity.getString(R.string.error_bad_password_format)
        rule.onNodeWithText(badPasswordErrorMessage)
            .assertIsDisplayed()
    }

    fun badPasswordErrorIsGone() {
        val badPasswordErrorMessage = rule.activity.getString(R.string.error_bad_password_format)
        rule.onNodeWithText(badPasswordErrorMessage)
            .assertDoesNotExist()
    }

    fun loginErrorMessageIsShown() {
        val invalidCredentialsError = rule.activity.getString(R.string.error_invalid_credentials)
        rule.onNodeWithText(invalidCredentialsError)
            .assertIsDisplayed()
    }

    fun userLoggedInSuccessfully() {
        rule.onNodeWithText("Timeline")
            .assertIsDisplayed()
    }
}
