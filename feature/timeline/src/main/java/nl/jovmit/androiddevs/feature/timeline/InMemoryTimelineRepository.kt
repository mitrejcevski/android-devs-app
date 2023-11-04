package nl.jovmit.androiddevs.feature.timeline

class InMemoryTimelineRepository(
    private val timelineData: List<ListItem> = emptyList()
) : TimelineRepository {

    override suspend fun loadTimeline(page: Int, pageSize: Int): TimelineResponse {
        val results = timelineData.chunked(pageSize)
        val previousKey = if (page == 0) null else page - 1
        val nextKey = if (page == results.lastIndex) null else page + 1

        return TimelineResponse.Success(
            data = results[page],
            previousPage = previousKey,
            nextPage = nextKey
        )
    }
}