package nl.jovmit.androiddevs.feature.timeline

interface TimelineRepository {

    suspend fun loadTimeline(page: Int, pageSize: Int): TimelineResponse
}