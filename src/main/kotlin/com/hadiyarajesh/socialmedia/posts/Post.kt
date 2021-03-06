package com.hadiyarajesh.socialmedia.posts

import com.hadiyarajesh.socialmedia.users.User
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.Instant

data class Post(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val postId: Long,
    val mediaType: String,
    val caption: String?,
    val createdAt: Instant,
    val totalLikes: Long,
    val totalComments: Long,

    @Relationship(type = "CREATED_POST", direction = Relationship.Direction.INCOMING)
    val user: User
)