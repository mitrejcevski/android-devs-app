package nl.jovmit.androiddevs.shared.ui.datetime

interface AppDateTimeFormat {

    fun toDateTime(zonedDateTime: String): String

    fun toDateTime(dateTimeMillis: Long): String
}