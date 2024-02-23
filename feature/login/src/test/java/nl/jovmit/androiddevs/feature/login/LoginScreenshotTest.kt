package nl.jovmit.androiddevs.feature.login

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import nl.jovmit.androiddevs.core.view.theme.AppTheme
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
                    onNavigateUp = {},
                    onEmailUpdate = {},
                    onPasswordUpdate = {},
                    onLoginClicked = {}
                )
            }
        }
    }
}