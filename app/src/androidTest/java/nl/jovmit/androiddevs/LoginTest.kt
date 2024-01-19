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

    @Test
    fun resetBadEmailFormatError() {
        launchLoginScreen(loginTestRule) {
            typeEmail("email")
            submit()
            typeEmail("email@")
        } verify {
            badEmailErrorIsGone()
        }
    }

    @Test
    fun displayBadPasswordError() {
        launchLoginScreen(loginTestRule) {
            typeEmail("email@email.com")
            typePassword("bad pass")
            submit()
        } verify {
            badPasswordErrorIsShown()
        }
    }
}





