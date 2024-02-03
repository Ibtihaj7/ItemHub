package com.example.itemhub.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemhub.model.Post
import com.example.itemhub.model.PostItem
import com.example.itemhub.repo.post.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepo: PostRepo
) : ViewModel() {
    private val _mutablePostsList: MutableLiveData<List<PostItem>> = MutableLiveData()
    val postsList: LiveData<List<PostItem>> = _mutablePostsList

    private val _favoritesLiveData = MutableLiveData<List<PostItem>>()
    val favoritesLiveData: LiveData<List<PostItem>> = _favoritesLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        updatePostsList()
    }

    private fun updatePostsList() {
        viewModelScope.launch {
            _isLoading.value = true
            _mutablePostsList.value = postRepo.getAllPosts()
            val res = postRepo.getFavoritePosts()
            _favoritesLiveData.value = res

            Log.d("main",res.size.toString())

            _isLoading.value = false
        }
    }

    fun filterList(query: String?) {
        viewModelScope.launch {
            val allData = postRepo.getAllPosts()
            if (query.isNullOrBlank()) {
                _mutablePostsList.value = allData
                return@launch
            }

            _mutablePostsList.value = allData.filter { post ->
                post.getTitle().contains(query, ignoreCase = true)
            }
        }
    }

    fun onFavoriteChanged(post: PostItem) {
        if (!post.getFavoriteState()) {
            postRepo.addToFavorites(post)
        } else {
            postRepo.removeFromFavorites(post)
        }
        updatePostsList()
    }

    fun loadPostsData() {
        viewModelScope.launch {
            _mutablePostsList.value = postRepo.getAllPosts(true)
            _favoritesLiveData.value = postRepo.getFavoritePosts()
        }
    }
}