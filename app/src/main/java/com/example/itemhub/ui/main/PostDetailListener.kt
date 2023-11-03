package com.example.itemhub.ui.main

import com.example.itemhub.model.Post

interface PostDetailListener {
    fun onCardViewClicked(post: Post)
}