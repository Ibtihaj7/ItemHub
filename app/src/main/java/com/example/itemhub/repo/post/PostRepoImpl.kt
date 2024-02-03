package com.example.itemhub.repo.post

import android.util.Log
import com.example.itemhub.model.Post
import com.example.itemhub.model.PostItem
import com.example.itemhub.repo.postclient.PostsClientRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepoImpl @Inject constructor(
    private val postsClientRepo: PostsClientRepo
) : PostRepo {
    private val allPostsList: ArrayList<PostItem> = ArrayList()
    private val favoritePostsIdList: MutableSet<Int> = mutableSetOf()

    override suspend fun getAllPosts(forceRefresh: Boolean): List<PostItem> {
        delay(1000L)
        val posts : List<Post>
        if (allPostsList.isEmpty() || forceRefresh) {
             posts = getPostsFromApi()
            allPostsList.clear()

            val postItem:List<PostItem> = posts.map {
                val favorite :Boolean = ifExist(it.getId())
                val obj = PostItem(it.getUserId(),it.getId(),it.getTitle(),it.getBody(),favorite)
                obj
            }
            allPostsList.addAll(postItem)
        }else{
            allPostsList.clear()
            allPostsList.addAll(allPostsList.map{
                val favorite :Boolean = ifExist(it.getId())
                val obj = PostItem(it.getUserId(),it.getId(),it.getTitle(),it.getBody(),favorite)
                obj
            }
            )
        }

        return allPostsList.toList()
    }

    private fun ifExist(id: Int) =
        favoritePostsIdList.any() {
            id == it
        }

    override suspend fun getFavoritePosts(): List<PostItem> {
        val allPosts = getAllPosts()
        return allPosts.filter { post ->
            post.getFavoriteState()
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

    override fun addToFavorites(post: PostItem) {
        favoritePostsIdList.add(post.getId())
    }

    override fun removeFromFavorites(post: PostItem) {
        favoritePostsIdList.remove(post.getId())
    }

    override fun getPost(postId: Int): PostItem {
        return allPostsList.first { post ->
            post.getId() == postId
        }
    }
}