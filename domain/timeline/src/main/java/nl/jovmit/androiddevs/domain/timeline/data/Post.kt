package nl.jovmit.androiddevs.domain.timeline.data

import nl.jovmit.androiddevs.domain.auth.data.User

data class Post(
    val id: String,
    val author: User,
    val title: String,
    val body: String,
    val createdAtMillis: Long,
    val imageUrls: List<String> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val reactions: List<Reaction> = emptyList()
)

data class Comment(
    val id: String,
    val author: User,
    val body: String,
    val createdAtMillis: Long
)

data class Reaction(
    val user: User,
    val type: ReactionType
)

enum class ReactionType {
    LIKE,
    LOVE,
    SUPPORT,
    INSIGHTFUL,
    CELEBRATE
}

data class Tag(
    val name: String
)
