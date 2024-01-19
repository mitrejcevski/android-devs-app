package nl.jovmit.androiddevs.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jovmit.androiddevs.core.view.R
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.core.view.theme.PreviewLightDark

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
) {
    val state by loginViewModel.screenState.collectAsState()
    LoginScreenContent(
        screenState = state,
        onNavigateUp = onNavigateUp,
        onEmailUpdate = loginViewModel::updateEmail,
        onPasswordUpdate = loginViewModel::updatePassword,
        onLoginClicked = loginViewModel::login
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoginScreenContent(
    screenState: LoginScreenState,
    onNavigateUp: () -> Unit,
    onEmailUpdate: (newValue: String) -> Unit,
    onPasswordUpdate: (newValue: String) -> Unit,
    onLoginClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = AppTheme.colorScheme.onBackground
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.login_title),
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
                .padding(paddingValues)
                .padding(horizontal = AppTheme.size.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.medium)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("email"),
                value = screenState.email,
                onValueChange = onEmailUpdate,
                isError = screenState.isWrongEmailFormat,
                supportingText = {
                    if (screenState.isWrongEmailFormat) {
                        Text(text = stringResource(id = R.string.error_bad_email_format))
                    }
                },
                label = {
                    Text(text = "Email")
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("password"),
                value = screenState.password,
                onValueChange = onPasswordUpdate,
                isError = screenState.isBadPasswordFormat,
                supportingText = {
                    if (screenState.isBadPasswordFormat) {
                        Text(text = stringResource(id = R.string.error_bad_password_format))
                    }
                },
                label = {
                    Text(text = "Password")
                }
            )
            if (screenState.wrongCredentials) {
                Text(text = stringResource(id = R.string.error_invalid_credentials))
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("loginButton"),
                onClick = onLoginClicked
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewLoginScreen() {
    AppTheme {
        LoginScreenContent(
            screenState = LoginScreenState(),
            onNavigateUp = {},
            onEmailUpdate = {},
            onPasswordUpdate = {},
            onLoginClicked = {}
        )
    }
}