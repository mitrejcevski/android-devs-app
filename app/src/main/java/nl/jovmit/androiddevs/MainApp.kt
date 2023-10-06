package nl.jovmit.androiddevs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.jovmit.androiddevs.feature.login.loginScreen
import nl.jovmit.androiddevs.feature.login.navigateToLogin
import nl.jovmit.androiddevs.feature.signup.navigateToSignUp
import nl.jovmit.androiddevs.feature.signup.signUpScreen
import nl.jovmit.androiddevs.feature.welcome.WELCOME_ROUTE
import nl.jovmit.androiddevs.feature.welcome.welcomeScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = WELCOME_ROUTE
    ) {
        welcomeScreen(
            onLogin = { navController.navigateToLogin() },
            onSignUp = { navController.navigateToSignUp() }
        )

        signUpScreen(
            onNavigateUp = { navController.navigateUp() }
        )

        loginScreen(
            onNavigateUp = { navController.navigateUp() }
        )
    }
}
