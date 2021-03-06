package com.hadiyarajesh.socialmedia.comments

import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.users.User
import com.hadiyarajesh.socialmedia.posts.PostRepository
import com.hadiyarajesh.socialmedia.users.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun createComment(userId: Long, postId: Long, commentId: Long, text: String): Comment {
        val user = userRepository.findByUserId(userId)
            ?: throw ResourceNotFound("User $userId not found")

        if (!postRepository.existsByPostId(postId)) {
            throw ResourceNotFound("Post $postId not found")
        }

        val comment = commentRepository.save(
            Comment(
                commentId = commentId,
                text = text,
                createdAt = Instant.now(),
                totalLikes = 0,
                user = user
            )
        )

        return if (commentRepository.attachCommentToPost(postId, commentId)) {
            comment
        } else {
            throw ResourceNotFound("Post $postId not found")
        }
    }

    fun getComment(postId: Long, commentId: Long): Comment? {
        return commentRepository.getComment(postId, commentId)
    }

    fun editComment(userId: Long, commentId: Long, text: String): Comment {
        return commentRepository.editCommentText(userId, commentId, text)
            ?: throw ResourceNotFound("Comment $commentId by User $userId not found")
    }

    fun deleteComment(userId: Long, postId: Long, commentId: Long): Boolean {
        return commentRepository.deleteComment(userId, postId, commentId)
            ?: throw ResourceNotFound("Comment $commentId by User $userId not found")
    }

    fun getAllCommentsByPost(postId: Long, page: Int, size: Int): Slice<Comment> {
        val pageable = PageRequest.of(page, size)
        return commentRepository.getAllCommentsByPost(postId, pageable)
    }

       fun getPostCommenters(postId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.getPostCommenters(postId, pageable)
    }

    fun getTotalCommentersCountByPost(postId: Long): Int {
        return commentRepository.getTotalCommentersCountByPost(postId)
    }

    fun deleteAllCommentsByPost(postId: Long) {
        commentRepository.deleteAllCommentsByPost(postId)
    }

    fun deleteAllCommentsByUser(userId: Long) {
        commentRepository.deleteAllCommentsByUser(userId)
    }
}