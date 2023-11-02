package com.example.itemhub.repo.post

import com.example.itemhub.model.Post

interface PostRepo {
    suspend fun getAllPosts(forceRefresh: Boolean = false): List<Post>

    suspend fun getFavoritePosts(): List<Post>

    fun addToFavorites(post: Post)
    fun removeFromFavorites(post: Post)
}