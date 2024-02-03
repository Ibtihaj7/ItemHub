package com.example.itemhub.ui.main

import com.example.itemhub.model.PostItem

interface PostDetailListener {
    fun onCardViewClicked(post: PostItem)
}