package nl.jovmit.androiddevs.feature.postdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.jovmit.androiddevs.core.view.theme.AppTheme
import java.util.UUID

private data class User(
    val userId: String,
    val name: String
)

private val data = listOf(
    User(userId = UUID.randomUUID().toString(), "Alice"),
    User(userId = UUID.randomUUID().toString(), "Adam"),
    User(userId = UUID.randomUUID().toString(), "Alexander"),
    User(userId = UUID.randomUUID().toString(), "Ava"),
    User(userId = UUID.randomUUID().toString(), "Andrew"),
    User(userId = UUID.randomUUID().toString(), "Adriana"),
    User(userId = UUID.randomUUID().toString(), "Aaron"),
    User(userId = UUID.randomUUID().toString(), "Anna"),
    User(userId = UUID.randomUUID().toString(), "Bella"),
    User(userId = UUID.randomUUID().toString(), "Bianca"),
    User(userId = UUID.randomUUID().toString(), "Bob"),
    User(userId = UUID.randomUUID().toString(), "Brielle"),
    User(userId = UUID.randomUUID().toString(), "Blake"),
    User(userId = UUID.randomUUID().toString(), "Brynn"),
    User(userId = UUID.randomUUID().toString(), "Barrett"),
    User(userId = UUID.randomUUID().toString(), "Bryson"),
    User(userId = UUID.randomUUID().toString(), "Ben"),
    User(userId = UUID.randomUUID().toString(), "Bennet"),
    User(userId = UUID.randomUUID().toString(), "Benjamin"),
    User(userId = UUID.randomUUID().toString(), "Briana"),
    User(userId = UUID.randomUUID().toString(), "Bryce"),
    User(userId = UUID.randomUUID().toString(), "Banks"),
    User(userId = UUID.randomUUID().toString(), "Ben"),
)

@Composable
fun LazyXTest() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 50.dp)
    ) {
        items(
            items = data,
            key = { user -> user.userId }
        ) { user ->
            ListItem(
                modifier = Modifier.clickable { },
                colors = ListItemDefaults.colors(
                    containerColor = AppTheme.colorScheme.error
                ),
                headlineContent = {
                    Text(
                        text = user.name,
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.titleNormal
                    )
                },
                supportingContent = {
                    Text(
                        text = user.name,
                        color = AppTheme.colorScheme.onBackground,
                        style = AppTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun LazyXPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
        ) {
            LazyXTest()
        }
    }
}