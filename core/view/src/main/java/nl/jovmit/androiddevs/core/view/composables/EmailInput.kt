package nl.jovmit.androiddevs.core.view.composables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.androiddevs.core.view.R
import nl.jovmit.androiddevs.core.view.theme.AppTheme

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onEmailChanged: (newValue: String) -> Unit
) {
    TextInput(
        modifier = modifier,
        text = email,
        label = stringResource(id = R.string.enter_email),
        hint = stringResource(id = R.string.email_hint),
        onTextChanged = onEmailChanged,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
@PreviewLightDark
private fun EmailInputPreview() {
    AppTheme {
        EmailInput(
            email = "",
            onEmailChanged = {}
        )
    }
}