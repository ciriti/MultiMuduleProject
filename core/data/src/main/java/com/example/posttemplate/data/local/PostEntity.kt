package io.github.ciriti.data.local

data class PostEntity(
    val id: Int,
    val title: String,
    val content: String,
    val authorId: Int
)
