package com.example.itemhub.ui.main.home

import androidx.lifecycle.ViewModel
import com.example.itemhub.model.Post
import com.example.itemhub.repo.post.PostRepo
import com.example.itemhub.repo.post.PostRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepo: PostRepo
) : ViewModel() {
}
