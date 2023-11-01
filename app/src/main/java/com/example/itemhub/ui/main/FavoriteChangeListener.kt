package com.example.itemhub.ui.main

import com.example.itemhub.model.Post

interface FavoriteChangeListener {
    fun onFavoriteChanged(post: Post)
}