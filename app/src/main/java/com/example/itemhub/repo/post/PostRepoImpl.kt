package com.example.itemhub.repo.post

import android.util.Log
import com.example.itemhub.model.Post
import com.example.itemhub.repo.postclient.PostsClientRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepoImpl @Inject constructor(
    private val postsClientRepo: PostsClientRepo
) : PostRepo{
    private val allPostsList: ArrayList<Post> = ArrayList()
    private val favoritePostsIdList: MutableSet<Int> = mutableSetOf()

    override fun getAllPosts(): List<Post> = allPostsList.toList()
    override fun getFavoriteIdPosts(): Set<Int> = favoritePostsIdList
    override suspend fun fetchPosts() {
        if(allPostsList.isEmpty()){
            val posts = getPostsFromApi()
            allPostsList.addAll(posts)
        }
    }

    private suspend fun getPostsFromApi(): List<Post> = withContext(Dispatchers.IO) {
        try {
            val response = postsClientRepo.getPosts()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("PostRepoImpl", "Error in getPosts: ${e.message}", e)
            emptyList()
        }
    }

    override fun addToFavorites(post: Post) {
        favoritePostsIdList.add(post.getId())
    }

    override fun removeFromFavorites(post: Post) {
        favoritePostsIdList.remove(post.getId())
    }
}