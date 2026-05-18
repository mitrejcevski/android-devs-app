package nl.jovmit.androiddevs.feature.postdetails

import nl.jovmit.androiddevs.domain.timeline.data.Comment
import nl.jovmit.androiddevs.domain.timeline.data.Post

data class PostDetailsScreenState(
  val isLoading: Boolean = false,
  val postItem: PostDetailsItem? = null,
  val isNotFound: Boolean = false,
  val isOffline: Boolean = false,
  val isUnavailable: Boolean = false,
  val isRemoved: Boolean = false
)

data class PostDetailsItem(
  val post: Post,
  val canRemove: Boolean
)

fun Comment.toCommentItem(): CommentItem = CommentItem(
  author = author.email,
  body = body,
  createdAt =  createdAtMillis
)

data class CommentItem(
  val author: String,
  val body: String,
  val createdAt: Long
)
