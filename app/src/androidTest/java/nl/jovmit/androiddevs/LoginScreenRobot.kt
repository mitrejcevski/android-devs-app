package nl.jovmit.androiddevs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule

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
        rule.onNodeWithText("Bad email format")
            .assertIsDisplayed()
    }
}
