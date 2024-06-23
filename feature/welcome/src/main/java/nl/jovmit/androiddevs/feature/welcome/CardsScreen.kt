package nl.jovmit.androiddevs.feature.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.jovmit.androiddevs.core.view.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    onNavigateToCardDetails: (cardType: String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Cards") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.colorScheme.background,
                    titleContentColor = AppTheme.colorScheme.onBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ColorCard(
                label = "High",
                color = AppTheme.cardColorScheme.high,
                onClick = { onNavigateToCardDetails("high") }
            )

            ColorCard(
                label = "Medium",
                color = AppTheme.cardColorScheme.medium,
                onClick = { onNavigateToCardDetails("medium") }
            )

            ColorCard(
                label = "Low",
                color = AppTheme.cardColorScheme.low,
                onClick = { onNavigateToCardDetails("low") }
            )
        }
    }
}

@Composable
private fun ColorCard(
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(AppTheme.size.normal),
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = label,
            style = AppTheme.typography.titleLarge
        )
    }
}

@Composable
@PreviewLightDark
private fun CardsScreenPreview() {
    AppTheme {
        CardsScreen(
            onNavigateToCardDetails = {}
        )
    }
}