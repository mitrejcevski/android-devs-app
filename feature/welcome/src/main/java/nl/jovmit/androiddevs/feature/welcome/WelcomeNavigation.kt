package nl.jovmit.androiddevs.feature.welcome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object Welcome

fun NavGraphBuilder.welcomeScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    composable<Welcome> {
        WelcomeScreen(
            onLogin = onLogin,
            onSignUp = onSignUp
        )
    }
}

@Serializable
object Cards

@Serializable
data class CardDetails(val cardType: String)

fun NavGraphBuilder.cardsScreen(
    onNavigateToCardDetails: (cardType: String) -> Unit
) {
    composable<Cards> {
        CardsScreen(
            onNavigateToCardDetails = onNavigateToCardDetails
        )
    }
}

fun NavGraphBuilder.cardDetailsScreen(
    onNavigateUp: () -> Unit
) {
    composable<CardDetails> { backStackEntry ->
        val cardDetails: CardDetails = backStackEntry.toRoute()
        CardDetailsScreen(
            cardDetails,
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToCardDetails(cardType: String) {
    navigate(CardDetails(cardType))
}
