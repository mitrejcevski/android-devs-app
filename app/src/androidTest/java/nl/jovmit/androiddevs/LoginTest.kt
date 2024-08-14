package nl.jovmit.androiddevs

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import nl.jovmit.androiddevs.domain.auth.AuthModule
import nl.jovmit.androiddevs.domain.auth.AuthRepository
import nl.jovmit.androiddevs.domain.auth.InMemoryAuthRepository
import nl.jovmit.androiddevs.domain.auth.data.User
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AuthModule::class)
class LoginTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val loginTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val usersCatalog: AuthRepository = InMemoryAuthRepository(usersForPassword = emptyMap())

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

    @Test
    fun resetBadPasswordFormatError() {
        launchLoginScreen(loginTestRule) {
            typeEmail("email@email.com")
            typePassword("bad pass")
            submit()
            typePassword("bad pass1")
        } verify {
            badPasswordErrorIsGone()
        }
    }

    @Test
    fun errorLoggingIn() {
        launchLoginScreen(loginTestRule) {
            typeEmail("email@email.com")
            typePassword("passWord12.")
            submit()
        } verify {
            loginErrorMessageIsShown()
        }
    }

    @Test
    fun successfulLogin() {
        val email = "email@email.com"
        val password = "passWord12."
        setupAuthRepositoryWith(email, password)

        launchLoginScreen(loginTestRule) {
            typeEmail(email)
            typePassword(password)
            submit()
        } verify {
            userLoggedInSuccessfully()
        }
    }

    private fun setupAuthRepositoryWith(email: String, password: String) {
        (usersCatalog as InMemoryAuthRepository)
            .setLoggedInUsers(mapOf(password to listOf(User("", email, ""))))
    }
}





