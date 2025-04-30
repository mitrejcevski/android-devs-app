package nl.jovmit.androiddevs.shared.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.androiddevs.shared.ui.R
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String,
    isInvalidPasswordFormat: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onPasswordChanged: (newValue: String) -> Unit,
    testTag: String = "password"
) {
    var isPasswordHidden by remember { mutableStateOf(true) }
    val visualTransformation = if (isPasswordHidden) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
    TextInput(
        modifier = modifier,
        text = password,
        label = stringResource(id = R.string.enter_password),
        hint = stringResource(id = R.string.password_hint),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        onTextChanged = onPasswordChanged,
        visualTransformation = visualTransformation,
        trailingIcon = {
            val drawable = if (isPasswordHidden) Default.VisibilityOff else Default.Visibility
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = if (isPasswordHidden) {
                        stringResource(id = R.string.cd_show_password)
                    } else {
                        stringResource(id = R.string.cd_hide_password)
                    },
                    onClick = { isPasswordHidden = !isPasswordHidden }
                ),
                imageVector = drawable,
                contentDescription = null,
                tint = AppTheme.colorScheme.onBackground
            )
        },
        error = {
            if (isInvalidPasswordFormat) {
                Text(
                    text = stringResource(id = R.string.error_bad_password_format),
                    color = AppTheme.colorScheme.error
                )
            }
        },
        testTag = testTag
    )
}

@Composable
@PreviewLightDark
private fun PreviewPasswordInput() {
    AppTheme {
        PasswordInput(
            password = "something",
            onPasswordChanged = {}
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewPasswordInputWithHint() {
    AppTheme {
        PasswordInput(
            password = "",
            onPasswordChanged = {}
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewPasswordInputWithError() {
    AppTheme {
        PasswordInput(
            password = "",
            isInvalidPasswordFormat = true,
            onPasswordChanged = {}
        )
    }
}