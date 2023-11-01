package com.example.itemhub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itemhub.model.Post
import com.example.itemhub.repo.post.PostRepo
import com.example.itemhub.repo.post.PostRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val postRepo: PostRepo
):ViewModel() {
    private val mutablePostsList: MutableLiveData<List<Post>> = MutableLiveData()
    private val mutableFavoritePostsList: MutableLiveData<Set<Int>> = MutableLiveData()
    init {
        updatePostsList()
    }
    fun getPostsList(): LiveData<List<Post>> = mutablePostsList
    fun getFavoritePostsList(): LiveData<Set<Int>> = mutableFavoritePostsList

    private fun updatePostsList() {
        viewModelScope.launch{
            postRepo.fetchPosts()
            mutablePostsList.value = postRepo.getAllPosts()
            mutableFavoritePostsList.value = postRepo.getFavoriteIdPosts()
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