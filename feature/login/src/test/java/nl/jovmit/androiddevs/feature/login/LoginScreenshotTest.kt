package nl.jovmit.androiddevs.feature.login

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun defaultLoginScreen() {
        paparazzi.snapshot {
            AppTheme {
                LoginScreenContent(
                    screenState = LoginScreenState(),
                    loginActions = EmptyLoginActions(),
                    onNavigateUp = {}
                )
            }
        }
    }

    private class EmptyLoginActions: LoginActions {
        override fun updateEmail(newValue: String) {
            TODO("Not yet implemented")
        }

        override fun updatePassword(newValue: String) {
            TODO("Not yet implemented")
        }

        override fun login() {
            TODO("Not yet implemented")
        }
    }
}