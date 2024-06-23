package nl.jovmit.androiddevs.feature.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsScreen(
    cardDetails: CardDetails,
    onNavigateUp: () -> Unit
) {
    val containerColor = colorForCardType(type = cardDetails.cardType)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = containerColor,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Up",
                            tint = AppTheme.colorScheme.onBackground
                        )
                    }
                },
                title = { Text(text = cardDetails.cardType.capitalize(Locale.getDefault())) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = containerColor,
                    titleContentColor = AppTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hello, World",
                color = AppTheme.colorScheme.onBackground,
                style = AppTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun colorForCardType(type: String): Color {
    return when (type) {
        "high" -> AppTheme.cardColorScheme.high
        "medium" -> AppTheme.cardColorScheme.medium
        else -> AppTheme.cardColorScheme.low
    }
}

@Composable
@PreviewLightDark
private fun PreviewCardDetailsScreenHigh() {
    AppTheme {
        CardDetailsScreen(
            cardDetails = CardDetails(cardType = "high"),
            onNavigateUp = {}
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewCardDetailsScreenMedium() {
    AppTheme {
        CardDetailsScreen(
            cardDetails = CardDetails(cardType = "medium"),
            onNavigateUp = {}
        )
    }
}

@Composable
@PreviewLightDark
private fun PreviewCardDetailsScreenLow() {
    AppTheme {
        CardDetailsScreen(
            cardDetails = CardDetails(cardType = "low"),
            onNavigateUp = {}
        )
    }
}
