package com.hadiyarajesh.socialmedia.model

data class LikeRequest(
    val postId: Long,
    val commentId: Long? = null
)
