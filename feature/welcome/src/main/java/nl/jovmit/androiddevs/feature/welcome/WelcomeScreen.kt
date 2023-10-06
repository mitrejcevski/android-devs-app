package nl.jovmit.androiddevs.feature.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.jovmit.androiddevs.core.view.composables.PrimaryButton
import nl.jovmit.androiddevs.core.view.composables.SecondaryButton
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.core.view.theme.PreviewLightDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WelcomeScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Badass Android Devs",
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.titleNormal
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.colorScheme.background,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.large)
        ) {
            Text(
                text = "Welcome!",
                color = AppTheme.colorScheme.onBackground,
                style = AppTheme.typography.titleLarge
            )
            PrimaryButton(
                label = "Sign Up",
                onClick = onSignUp
            )
            SecondaryButton(
                label = "Login",
                onClick = onLogin
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewLoginScreen() {
    AppTheme {
        WelcomeScreen(
            onLogin = {},
            onSignUp = {}
        )
    }
}