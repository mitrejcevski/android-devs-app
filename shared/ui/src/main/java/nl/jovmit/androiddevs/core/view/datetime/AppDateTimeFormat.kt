package nl.jovmit.androiddevs.core.view.datetime

interface AppDateTimeFormat {

    fun toDateTime(zonedDateTime: String): String
}