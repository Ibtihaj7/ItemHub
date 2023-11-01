package com.example.itemhub.repo.post

import com.example.itemhub.model.Post

interface PostRepo {
    suspend fun fetchPosts()
    fun getAllPosts(): List<Post>
    fun getFavoriteIdPosts(): Set<Int>
    fun addToFavorites(post: Post)
    fun removeFromFavorites(post: Post)
}