package nl.jovmit.androiddevs.core.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.jovmit.androiddevs.core.view.R
import nl.jovmit.androiddevs.core.view.theme.AppTheme

@Composable
@Preview
private fun PreviewClips() {
    AppTheme {
        Column(
            modifier = Modifier.padding(AppTheme.size.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.large)
        ) {
            Box(
                modifier = Modifier
                    .clip(AppTheme.shape.circular)
                    .size(200.dp)
                    .background(AppTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Some long text going here",
                    color = AppTheme.colorScheme.onPrimary
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.imperfect_circle_shape),
                    contentDescription = null
                )
                Text(
                    text = "Some long text going here",
                    color = AppTheme.colorScheme.onPrimary
                )
            }
        }
    }
}