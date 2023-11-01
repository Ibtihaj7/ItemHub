package com.example.itemhub.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itemhub.R
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.CustomPostBinding
import com.example.itemhub.model.Post
import com.google.gson.Gson

class PostItemAdapter (
    private val context: Context,
    private var posts: List<Post>,
    private val favoriteChangeListener: FavoriteChangeListener
): RecyclerView.Adapter<PostItemViewHolder>() {
    fun updateData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomPostBinding.inflate(layoutInflater, parent, false)
        return PostItemViewHolder(binding, context, favoriteChangeListener)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val currentPost = posts[position]
        holder.bind(currentPost)
    }
}