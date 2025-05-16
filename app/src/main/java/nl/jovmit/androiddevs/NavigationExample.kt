package nl.jovmit.androiddevs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Serializable
data object HomeTab

@Serializable
data object Home

@Serializable
data object InsideHome

@Serializable
data object PrizesTab

@Serializable
data object Prizes

@Serializable
data object PrizeWon

@Serializable
data object BenefitsTab

@Serializable
data object Benefits

@Serializable
data object WinActionsTab

@Serializable
data object WinActions

@Serializable
data object AccountTab

@Serializable
data object Account

@Composable
fun NavigationExample() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            AppBottomBar(
                navController = navController,
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            startDestination = HomeTab,
            navController = navController
        ) {
            navigation<HomeTab>(
                startDestination = Home,
            ) {
                composable<Home> {
                    Screen(title = "Home") {
                        navController.navigate(InsideHome)
                    }
                }
                composable<InsideHome> {
                    Screen(title = "InsideHome") {
                        navController.navigateUp()
                    }
                }
            }

            navigation<PrizesTab>(
                startDestination = Prizes
            ) {
                composable<Prizes> {
                    Screen(title = "Prizes") {
                        navController.navigate(PrizeWon)
                    }
                }

                composable<PrizeWon>(
                    deepLinks = listOf(
                        navDeepLink<PrizeWon>(basePath = "soci://ali.zer/win") //go to the bottom of the file for more info on deep links
                    )
                ) {
                    Screen(title = "Reveal Won Prize") {
                        navController.navigateUp()
                    }
                }
            }

            navigation<BenefitsTab>(
                startDestination = Benefits
            ) {
                composable<Benefits> {
                    Screen(title = "Benefits") { }
                }
            }

            navigation<WinActionsTab>(
                startDestination = WinActions
            ) {
                composable<WinActions> {
                    Screen(title = "Win Actions") { }
                }
            }

            navigation<AccountTab>(
                startDestination = Account
            ) {
                composable<Account> {
                    Screen(title = "Account") { }
                }
            }
        }
    }
}

private data class BottomBarDestination(
    val destination: Any,
    val icon: ImageVector,
    val title: String
)

@Composable
private fun AppBottomBar(
    navController: NavController,
) {
    val destinations = listOf(
        BottomBarDestination(HomeTab, Icons.Default.Home, "Home"),
        BottomBarDestination(PrizesTab, Icons.Default.CardGiftcard, "Prizes"),
        BottomBarDestination(BenefitsTab, Icons.Default.Percent, "Benefits"),
        BottomBarDestination(WinActionsTab, Icons.Default.WineBar, "Wins"),
        BottomBarDestination(AccountTab, Icons.Default.Person, "Account"),
    )
    BottomNavigation(
        backgroundColor = AppTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        destinations.forEach { destination ->
            val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(destination.destination::class) } == true
            val color = if (isSelected) AppTheme.colorScheme.secondary else AppTheme.colorScheme.onBackground
            BottomNavigationItem(
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null,
                        tint = color
                    )
                },
                label = {
                    Text(
                        text = destination.title,
                        color = color
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(destination.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun Screen(
    title: String,
    onNavigate: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)
        Button(onClick = onNavigate) {
            Text(text = "Go")
        }
    }
}

/**
 * To enable the deep links - make sure to add an intent filter for the activity in the manifest
 * <intent-filter>
 *   <action android:name="android.intent.action.VIEW" />
 *
 *   <category android:name="android.intent.category.DEFAULT" />
 *   <category android:name="android.intent.category.BROWSABLE" />
 *
 *   <data android:host="ali.zer" android:scheme="soci" />
 * </intent-filter>
 *
 * And to test it, run this in the terminal:
 * adb shell am start -a android.intent.action.VIEW -d "soci://ali.zer/win" <app package here>
 */