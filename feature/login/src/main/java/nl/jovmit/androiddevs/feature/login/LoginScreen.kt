package nl.jovmit.androiddevs.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jovmit.androiddevs.core.view.R
import nl.jovmit.androiddevs.core.view.composables.PrimaryButton
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.core.view.theme.PreviewLightDark

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: (user: User) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val state by loginViewModel.screenState.collectAsState()
    LaunchedEffect(key1 = state.loggedInUser) {
        state.loggedInUser?.let { user -> onLoggedIn(user) }
    }
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
internal fun LoginScreenContent(
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.medium),
            horizontalAlignment = Alignment.CenterHorizontally
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
            Image(
                painter = painterResource(id = R.drawable.logo_android_devs),
                contentDescription = stringResource(id = R.string.cd_logo)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorPrompt(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = AppTheme.colorScheme.background,
            shape = RoundedCornerShape(AppTheme.size.normal)
        ) {
            Column(
                modifier = Modifier.padding(AppTheme.size.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.size.large)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Error",
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = errorMessage,
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = "OK",
                    onClick = {
                        onDismiss()
                    }
                )
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

@Composable
@PreviewLightDark
private fun PreviewErrorAlert() {
    AppTheme {
        ErrorPrompt(
            errorMessage = "Something went wrong",
            onDismiss = {}
        )
    }
}