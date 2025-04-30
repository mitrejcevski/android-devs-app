package nl.jovmit.androiddevs.shared.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    hint: String = "",
    singleLine: Boolean = true,
    borderColor: Color = AppTheme.colorScheme.separator,
    selectedBorderColor: Color = AppTheme.colorScheme.primary,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: (@Composable () -> Unit)? = null,
    error: (@Composable () -> Unit)? = null,
    onTextChanged: (newValue: String) -> Unit,
    testTag: String = ""
) {
    var actualBorderColor by remember { mutableStateOf(borderColor) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.size.small)
    ) {
        Text(
            text = label,
            color = AppTheme.colorScheme.onBackground,
            style = AppTheme.typography.labelNormal.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AppTheme.shape.container)
                .border(
                    width = 1.dp,
                    color = actualBorderColor,
                    shape = AppTheme.shape.container
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag(testTag)
                            .onFocusChanged { state ->
                                val actualColor = if (state.isFocused) selectedBorderColor else borderColor
                                actualBorderColor = actualColor
                            }
                            .padding(horizontal = AppTheme.size.small),
                        value = text,
                        singleLine = singleLine,
                        onValueChange = onTextChanged,
                        textStyle = AppTheme.typography.paragraph.copy(
                            color = AppTheme.colorScheme.onBackground
                        ),
                        cursorBrush = SolidColor(AppTheme.colorScheme.onBackground),
                        visualTransformation = visualTransformation,
                        keyboardActions = keyboardActions,
                        keyboardOptions = keyboardOptions
                    )
                    if (text.isBlank()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppTheme.size.small),
                            text = hint,
                            style = AppTheme.typography.paragraph,
                            color = AppTheme.colorScheme.onBackground.copy(alpha = .5f)
                        )
                    }
                }
                trailingIcon?.let { icon ->
                    Box(
                        modifier = Modifier
                            .padding(AppTheme.size.small)
                    ) {
                        icon()
                    }
                }
            }
        }
        if (error != null) {
            error()
        }
    }
}

@PreviewLightDark
@Composable
private fun TextInputPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .padding(AppTheme.size.normal)
        ) {
            var text by remember { mutableStateOf("some value") }
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                label = "Enter Name",
                hint = "email@email.com",
                onTextChanged = { text = it }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TextInputPreviewWithHint() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .padding(AppTheme.size.normal)
        ) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = "",
                label = "Enter Name",
                hint = "email@company.com",
                onTextChanged = {}
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun TextInputPreviewWithVisualTransformation() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .padding(AppTheme.size.normal)
        ) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = "something",
                label = "Enter Pass",
                onTextChanged = {},
                visualTransformation = PasswordVisualTransformation()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TextInputPreviewWithTrailingIcon() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .padding(AppTheme.size.normal)
        ) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = "something",
                label = "Enter Pass",
                onTextChanged = {},
                visualTransformation = PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = AppTheme.colorScheme.onBackground
                    )
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TextInputPreviewWithError() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .padding(AppTheme.size.normal)
        ) {
            TextInput(
                modifier = Modifier.fillMaxWidth(),
                text = "something",
                label = "Enter Pass",
                onTextChanged = {},
                visualTransformation = PasswordVisualTransformation(),
                error = {
                   Text(
                       text = "Error of some kind",
                       color = AppTheme.colorScheme.onBackground
                   )
                }
            )
        }
    }
}
