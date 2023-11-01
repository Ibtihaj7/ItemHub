package com.example.itemhub.di

import android.os.Handler
import android.os.Looper
import com.example.itemhub.repo.post.PostRepo
import com.example.itemhub.repo.post.PostRepoImpl
import com.example.itemhub.repo.postclient.PostsClientRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.gson.Gson
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
    fun providePostsClientRepo(): PostsClientRepo {
        return PostsClientRepo()
    }
    @Singleton
    @Provides
    fun provideHandler():Handler{
        return Handler(Looper.getMainLooper())
    }
    @Singleton
    @Provides
    fun providePostRepo(postsClientRepo: PostsClientRepo): PostRepo {
        return PostRepoImpl(postsClientRepo)
    }
}