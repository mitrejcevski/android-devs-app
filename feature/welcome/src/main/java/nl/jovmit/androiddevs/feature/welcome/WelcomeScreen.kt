package nl.jovmit.androiddevs.feature.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.androiddevs.core.view.R
import nl.jovmit.androiddevs.core.view.composables.PrimaryButton
import nl.jovmit.androiddevs.core.view.composables.SecondaryButton
import nl.jovmit.androiddevs.core.view.theme.AppTheme

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
                        text = stringResource(id = R.string.app_name),
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
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier.weight(.8f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_message),
                    color = AppTheme.colorScheme.onBackground,
                    style = AppTheme.typography.titleLarge
                )
            }
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.logo_android_devs),
                contentDescription = stringResource(id = R.string.cd_logo)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(AppTheme.size.large),
                verticalArrangement = Arrangement.spacedBy(
                    space = AppTheme.size.small,
                    alignment = Alignment.Bottom
                )
            ) {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = R.string.sign_up_title),
                    onClick = onSignUp
                )
                SecondaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(id = R.string.login_title),
                    onClick = onLogin
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PreviewLoginScreen() {
    AppTheme {
        WelcomeScreen(
            onLogin = {},
            onSignUp = {}
        )
    }
}