package com.example.itemhub.ui.main.favorite

import androidx.lifecycle.ViewModel
import com.example.itemhub.model.Post
import com.example.itemhub.repo.post.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritePostsViewModel @Inject constructor(
    private val postRepo: PostRepo
) : ViewModel() {
    fun filterData(): List<Post> {
        val favoritePostsIdList: Set<Int> = postRepo.getFavoriteIdPosts()
        return postRepo.getAllPosts().filter { post ->
            post.getId() in favoritePostsIdList
        }.toList()
    }
}
