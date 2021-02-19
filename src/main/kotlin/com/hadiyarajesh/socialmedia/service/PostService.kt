package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.model.Post
import com.hadiyarajesh.socialmedia.model.PostRequest
import com.hadiyarajesh.socialmedia.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class PostService(
    private val userService: UserService,
    private val postRepository: PostRepository

) {
    fun createPost(userId: Long, postRequest: PostRequest): Post {
        val user = userService.getUserByUserId(userId)

        val post = Post(
            postId = postRequest.postId,
            mediaType = postRequest.mediaType,
            caption = postRequest.caption,
            createdAt = Instant.now(),
            user = user
        )
        return postRepository.save(post)
    }
}