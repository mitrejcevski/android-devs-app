package nl.jovmit.androiddevs.feature.welcome

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object WelcomeRoute : NavKey

fun EntryProviderScope<NavKey>.welcomeEntry(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    entry<WelcomeRoute> {
        WelcomeScreen(
            onLogin = onLogin,
            onSignUp = onSignUp
        )
    }
}
