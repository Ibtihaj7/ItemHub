package com.example.itemhub.ui.main

import com.example.itemhub.model.PostItem

interface FavoriteChangeListener {
    fun onFavoriteChanged(post: PostItem)
}