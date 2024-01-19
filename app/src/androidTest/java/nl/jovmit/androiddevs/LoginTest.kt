package nl.jovmit.androiddevs

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @get:Rule
    val loginTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun displayBadEmailFormatError() {
        launchLoginScreen(loginTestRule) {
            typeEmail("email")
            submit()
        } verify {
            badEmailErrorIsShown()
        }
    }
}





