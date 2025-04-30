package nl.jovmit.androiddevs.shared.ui.datetime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.androiddevs.shared.ui.theme.AppTheme

@Composable
@PreviewLightDark
private fun TryOutDateTimeFormatter() {
    AppTheme {
        Column(modifier = Modifier.background(AppTheme.colorScheme.background)) {
            val formatter = AppDateTime.formatter
            val formatted = formatter.toDateTime("2025-01-08T15:17:13.791Z")
            Text(text = formatted, color = AppTheme.colorScheme.onBackground)
        }
    }
}