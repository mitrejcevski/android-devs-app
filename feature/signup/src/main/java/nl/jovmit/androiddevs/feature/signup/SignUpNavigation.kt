package nl.jovmit.androiddevs.feature.signup

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute : NavKey

fun EntryProviderScope<NavKey>.signUpEntry(
    onNavigateUp: () -> Unit,
) {
    entry<SignUpRoute> {
        SignUpScreen(
            onNavigateUp = onNavigateUp,
        )
    }
}
