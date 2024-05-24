package nl.jovmit.androiddevs.feature.welcome

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class WelcomeScreenTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun defaultWelcomeScreen() {
        paparazzi.snapshot {
            AppTheme {
                WelcomeScreen(
                    onLogin = {},
                    onSignUp = {}
                )
            }
        }
    }
}