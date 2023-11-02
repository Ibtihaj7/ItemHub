package com.example.itemhub.repo.postclient

import com.example.itemhub.model.Post
import com.example.itemhub.network.PostInterface
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsClientRepo @Inject constructor(
    private val postInterface: PostInterface
) {

    suspend fun getPosts(): Response<List<Post>> {
        return postInterface.getPost()
    }
}