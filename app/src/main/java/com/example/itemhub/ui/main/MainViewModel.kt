package com.example.itemhub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemhub.model.Post
import com.example.itemhub.repo.post.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepo: PostRepo
) : ViewModel() {
    private val _mutablePostsList: MutableLiveData<List<Post>> = MutableLiveData()
    val postsList: LiveData<List<Post>> = _mutablePostsList

    private val _favoritesLiveData = MutableLiveData<List<Post>>()
    val favoritesLiveData: LiveData<List<Post>> = _favoritesLiveData

    init {
        updatePostsList()
    }

    private fun updatePostsList() {
        viewModelScope.launch {
            _mutablePostsList.value = postRepo.getAllPosts()
            _favoritesLiveData.value = postRepo.getFavoritePosts()
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

    fun onFavoriteChanged(post: Post) {
        if (post.getFavoriteState()) {
            postRepo.addToFavorites(post)
        } else {
            postRepo.removeFromFavorites(post)
        }
        updatePostsList()
    }
}