package nl.jovmit.androiddevs.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import nl.jovmit.androiddevs.core.view.R
import nl.jovmit.androiddevs.core.view.composables.EmailInput
import nl.jovmit.androiddevs.core.view.composables.PasswordInput
import nl.jovmit.androiddevs.core.view.composables.PrimaryButton
import nl.jovmit.androiddevs.core.view.composables.TextInput
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import nl.jovmit.androiddevs.feature.signup.state.SignUpScreenState

@Composable
internal fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {

    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    SignUpScreenContent(
        signUpScreenState = screenState,
        onEmailChanged = viewModel::updateEmail,
        onPasswordChanged = viewModel::updatePassword,
        onAboutChanged = viewModel::updateAbout,
        onSignUp = {},
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpScreenContent(
    signUpScreenState: SignUpScreenState,
    onEmailChanged: (newValue: String) -> Unit,
    onPasswordChanged: (newValue: String) -> Unit,
    onAboutChanged: (newValue: String) -> Unit,
    onSignUp: () -> Unit,
    onNavigateUp: () -> Unit,
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
                        text = stringResource(R.string.sign_up_title),
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
        val verticalScroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.large)
        ) {
            Image(
                modifier = Modifier
                    .padding(AppTheme.size.large)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.logo_android_devs),
                contentDescription = stringResource(id = R.string.cd_logo)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .padding(horizontal = AppTheme.size.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.size.normal)
            ) {
                val passwordFocus = FocusRequester()
                val aboutFocus = FocusRequester()
                EmailInput(
                    modifier = Modifier.fillMaxWidth(),
                    email = signUpScreenState.email,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocus.requestFocus() }
                    ),
                    onEmailChanged = onEmailChanged,
                )
                PasswordInput(
                    modifier = Modifier
                        .focusRequester(passwordFocus)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { aboutFocus.requestFocus() }
                    ),
                    password = signUpScreenState.password,
                    onPasswordChanged = onPasswordChanged
                )
                TextInput(
                    modifier = Modifier
                        .focusRequester(aboutFocus)
                        .fillMaxWidth(),
                    text = signUpScreenState.about,
                    onTextChanged = onAboutChanged,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onSignUp() }
                    ),
                    label = stringResource(R.string.label_about),
                    hint = stringResource(R.string.bio_hint)
                )
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.sign_up_title),
                    onClick = onSignUp
                )
            }
        }
    }
}

@ShowkaseComposable(name = "Name of component", group = "Group Name")
@Composable
//@PreviewLightDark
fun PreviewLoginScreen() {
    AppTheme {
        SignUpScreenContent(
            signUpScreenState = SignUpScreenState(incorrectEmailFormat = true),
            onEmailChanged = {},
            onPasswordChanged = {},
            onAboutChanged = {},
            onSignUp = {},
            onNavigateUp = {}
        )
    }
}