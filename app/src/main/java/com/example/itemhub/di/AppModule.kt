package com.example.itemhub.di

import com.example.itemhub.network.PostInterface
import com.example.itemhub.repo.post.PostRepo
import com.example.itemhub.repo.post.PostRepoImpl
import com.example.itemhub.repo.postclient.PostsClientRepo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun providePostRepo(postsClientRepo: PostsClientRepo): PostRepo {
        return PostRepoImpl(postsClientRepo)
    }

    @Singleton
    @Provides
    fun providePostInterface(): PostInterface {
        val baseUrl = "https://jsonplaceholder.typicode.com/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PostInterface::class.java)
    }
}