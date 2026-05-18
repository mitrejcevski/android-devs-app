package nl.jovmit.androiddevs.domain.timeline

import nl.jovmit.androiddevs.domain.timeline.data.Post

sealed class TimelineResult {
    data class Success(val posts: List<Post>) : TimelineResult()
    data object Offline : TimelineResult()
    data object Unavailable : TimelineResult()
}

sealed class PostDetailsResult {
    data class Success(val post: Post) : PostDetailsResult()
    data object PostNotFound : PostDetailsResult()
    data object Offline : PostDetailsResult()
    data object Unavailable : PostDetailsResult()
}

sealed class AddPostResult {
    data class Success(val post: Post) : AddPostResult()
    data object InvalidContent : AddPostResult()
    data object NotSignedIn : AddPostResult()
    data object Offline : AddPostResult()
    data object Unavailable : AddPostResult()
}

sealed class RemovePostResult {
    data object Success : RemovePostResult()
    data object NotSignedIn : RemovePostResult()
    data object PostNotFound : RemovePostResult()
    data object NotAuthor : RemovePostResult()
    data object Offline : RemovePostResult()
    data object Unavailable : RemovePostResult()
}
