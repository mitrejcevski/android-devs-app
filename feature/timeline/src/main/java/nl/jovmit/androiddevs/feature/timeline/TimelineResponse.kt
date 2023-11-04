package nl.jovmit.androiddevs.feature.timeline

sealed class TimelineResponse {

    data class Success(
        val data: List<ListItem>,
        val previousPage: Int?,
        val nextPage: Int?
    ) : TimelineResponse()

    data object Error : TimelineResponse()
}
