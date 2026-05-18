package nl.jovmit.androiddevs.feature.login

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute : NavKey

fun EntryProviderScope<NavKey>.loginEntry(
    onLoggedIn: () -> Unit,
    onNavigateUp: () -> Unit
) {
    entry<LoginRoute> {
        LoginScreen(
            onLoggedIn = onLoggedIn,
            onNavigateUp = onNavigateUp
        )
    }
}
