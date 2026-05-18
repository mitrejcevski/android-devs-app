package nl.jovmit.androiddevs.domain.timeline

import kotlinx.coroutines.flow.Flow

interface TimelineRepository {

    val timeline: Flow<TimelineResult>

    suspend fun getPost(postId: String): PostDetailsResult

    suspend fun addPost(title: String, body: String): AddPostResult

    suspend fun removePost(postId: String): RemovePostResult
}
