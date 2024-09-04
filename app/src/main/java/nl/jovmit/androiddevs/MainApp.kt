package nl.jovmit.androiddevs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import nl.jovmit.androiddevs.feature.login.loginScreen
import nl.jovmit.androiddevs.feature.login.navigateToLogin
import nl.jovmit.androiddevs.feature.postdetails.navigateToPostDetails
import nl.jovmit.androiddevs.feature.postdetails.postDetailsScreen
import nl.jovmit.androiddevs.feature.signup.navigateToSignUp
import nl.jovmit.androiddevs.feature.signup.signUpScreen
import nl.jovmit.androiddevs.feature.timeline.navigateToTimeline
import nl.jovmit.androiddevs.feature.timeline.timelineScreen
import nl.jovmit.androiddevs.feature.welcome.WELCOME_ROUTE
import nl.jovmit.androiddevs.feature.welcome.welcomeScreen

@Composable
fun MainApp(
    mainViewModel: MainAppViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        mainViewModel.observeLoggedOut()
        mainViewModel.loggedOut.collect {
            navController.navigateToLogin()
        }
    }

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
            onLoggedIn = { navController.navigateToTimeline() },
            onNavigateUp = { navController.navigateUp() }
        )

        timelineScreen(
            onItemClicked = { itemId ->
                navController.navigateToPostDetails(itemId)
            }
        )

        postDetailsScreen(
            onNavigateUp = { navController.navigateUp() }
        )
    }
}
