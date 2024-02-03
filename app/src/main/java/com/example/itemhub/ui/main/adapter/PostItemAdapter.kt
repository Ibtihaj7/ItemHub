package com.example.itemhub.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.CustomPostBinding
import com.example.itemhub.model.PostItem
import com.example.itemhub.ui.main.PostDetailListener

class PostItemAdapter(
    private val context: Context,
    private val favoriteChangeListener: FavoriteChangeListener,
    private val postDetailListener : PostDetailListener
) : ListAdapter<PostItem, PostItemViewHolder>(DiffCallback()) {
    fun updateData(newPosts: List<PostItem>) {
       submitList(newPosts)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomPostBinding.inflate(layoutInflater, parent, false)
        return PostItemViewHolder(binding, context, favoriteChangeListener,postDetailListener)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val currentPost = getItem(position)
        holder.bind(currentPost)
    }

    class DiffCallback:DiffUtil.ItemCallback<PostItem>(){
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem) =
            oldItem.getId() == newItem.getId()

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem) =
            oldItem == newItem
    }
}