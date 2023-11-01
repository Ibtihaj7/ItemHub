package com.example.itemhub.repo.postclient

import com.example.itemhub.network.PostInterface
import com.example.itemhub.model.Post
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsClientRepo @Inject constructor() {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var postInterface: PostInterface? = null
    private fun getApi(): PostInterface {
        if(postInterface == null){
            postInterface = retrofit.create(PostInterface::class.java)
        }
        return postInterface!!
    }

    suspend fun getPosts(): Response<List<Post>> {
        if (postInterface == null) {
            postInterface = getApi()
        }
        return postInterface!!.getPost()
    }
}