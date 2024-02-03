package com.example.itemhub.ui.main.postdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itemhub.model.Post
import com.example.itemhub.model.PostItem
import com.example.itemhub.repo.post.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepo: PostRepo
): ViewModel() {
    private val _post = MutableLiveData<PostItem>()
    val post: LiveData<PostItem>
        get() = _post

    fun setPost(post: PostItem) {
        _post.value = post
    }

    fun getPost(postId: Int) = postRepo.getPost(postId)
}