package com.example.itemhub.repo.post

import com.example.itemhub.model.PostItem

interface PostRepo {
    suspend fun getAllPosts(forceRefresh: Boolean = false): List<PostItem>
    suspend fun getFavoritePosts(): List<PostItem>
    fun addToFavorites(post: PostItem)
    fun removeFromFavorites(post: PostItem)
    fun getPost(postId: Int): PostItem
}
