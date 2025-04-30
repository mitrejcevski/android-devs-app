package nl.jovmit.androiddevs.shared.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.jovmit.androiddevs.shared.ui.composables.ImperfectCircleShape
import nl.jovmit.androiddevs.shared.ui.datetime.LocalDateTimeFormat
import nl.jovmit.androiddevs.shared.ui.datetime.appZonedDateTimeFormat

private val darkColorScheme = AppColorScheme(
    background = Color.Black,
    onBackground = Purple80,
    primary = PurpleGrey40,
    onPrimary = PurpleGrey80,
    secondary = Pink40,
    onSecondary = Pink80,
    separator = LightGray,
    error = DarkRed
)

private val lightColorScheme = AppColorScheme(
    background = Color.White,
    onBackground = Purple40,
    primary = PurpleGrey80,
    onPrimary = PurpleGrey40,
    secondary = Pink80,
    onSecondary = Pink40,
    separator = DarkGray,
    error = LightRed
)

private val typography = AppTypography(
    titleLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleNormal = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    paragraph = TextStyle(
        fontFamily = OpenSans,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    labelNormal = TextStyle(
        fontFamily = OpenSans,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = OpenSans,
        fontSize = 12.sp
    )
)

private val shape = AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50),
    circular = ImperfectCircleShape()
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme
    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalDateTimeFormat provides appZonedDateTimeFormat,
        LocalIndication provides ripple(),
        content = content
    )
}

object AppTheme {

    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}