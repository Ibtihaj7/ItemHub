package com.example.itemhub.network

import com.example.itemhub.model.Post
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PostInterface {
    @GET("posts")
    suspend fun getPost(): Response<List<Post>>
}