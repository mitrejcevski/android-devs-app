package nl.jovmit.androiddevs.shared.ui.datetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

internal val LocalDateTimeFormat = staticCompositionLocalOf {
    passByFormat
}

internal val passByFormat = object : AppDateTimeFormat {
    override fun toDateTime(zonedDateTime: String): String {
        return zonedDateTime
    }

    override fun toDateTime(dateTimeMillis: Long): String {
        return "$dateTimeMillis"
    }
}

internal val appZonedDateTimeFormat = object : AppDateTimeFormat {

    override fun toDateTime(zonedDateTime: String): String {
        val date = ZonedDateTime.parse(zonedDateTime)
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDateTime()
        return DateTimeFormatter.ofPattern("DD MM yyyy").format(date)
    }

    override fun toDateTime(dateTimeMillis: Long): String {
        val dateTime = Instant.ofEpochMilli(dateTimeMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return DateTimeFormatter.ofPattern("DD MM yyyy").format(dateTime)
    }
}

object AppDateTime {

    val formatter: AppDateTimeFormat
        @Composable get() = LocalDateTimeFormat.current
}