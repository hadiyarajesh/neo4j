package com.example.neo4j.service

import com.example.neo4j.model.User
import com.example.neo4j.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FollowService(
    private val userRepository: UserRepository,
) {
    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
        if (currentUserId == userToFollowId) {
            throw IllegalArgumentException("You can not follow yourself")
        }

        userRepository.followUser(currentUserId, userToFollowId)
        return true
    }

    fun unfollowUser(currentUserId: Long, userToUnfollowId: Long): Boolean {
        if (currentUserId == userToUnfollowId) {
            throw IllegalArgumentException("You can not unfollow yourself")
        }

        userRepository.unfollowUser(currentUserId, userToUnfollowId)
        return true
    }

    fun getUserFollowing(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.getUserFollowing(currentUserId, pageable)
    }

    fun getUserFollowers(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.getUserFollowers(currentUserId, pageable)
    }
}