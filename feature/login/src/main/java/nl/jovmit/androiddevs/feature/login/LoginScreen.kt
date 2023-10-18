package nl.jovmit.androiddevs.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jovmit.androiddevs.core.view.composables.PrimaryButton
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.core.view.theme.PreviewLightDark

@Composable
internal fun LoginScreenContainer(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onNavigateUp: () -> Unit
) {

    val state by viewModel.screenState.collectAsStateWithLifecycle()
    LaunchedEffect(state.didAuthorize) {
        if (state.didAuthorize) {
            onLoggedIn()
        }
    }

    LoginScreen(
        screenState = state,
        onEmailUpdated = viewModel::updateEmail,
        onPasswordUpdated = viewModel::updatePassword,
        onAuthErrorDismissed = viewModel::clearAuthError,
        onLogin = { viewModel.login() },
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreen(
    screenState: LoginScreenState,
    onEmailUpdated: (newValue: String) -> Unit,
    onPasswordUpdated: (newValue: String) -> Unit,
    onAuthErrorDismissed: () -> Unit,
    onLogin: () -> Unit,
    onNavigateUp: () -> Unit
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
                        text = "Login",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier.padding(AppTheme.size.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.size.normal)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { TextInputLabel("Email") },
                    value = screenState.email,
                    onValueChange = onEmailUpdated
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { TextInputLabel("Password") },
                    value = screenState.password,
                    onValueChange = onPasswordUpdated
                )
                PrimaryButton(
                    label = "Login",
                    onClick = onLogin
                )
            }
        }
        if (screenState.isAuthError) {
            ErrorPrompt(
                errorMessage = "Incorrect credentials",
                onDismiss = onAuthErrorDismissed
            )
        }
    }
}

@Composable
private fun TextInputLabel(value: String) {
    Text(
        text = value,
        style = AppTheme.typography.labelSmall,
        color = AppTheme.colorScheme.onBackground
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorPrompt(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
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
        LoginScreen(
            screenState = LoginScreenState(),
            onEmailUpdated = {},
            onPasswordUpdated = {},
            onAuthErrorDismissed = {},
            onLogin = {},
            onNavigateUp = {}
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