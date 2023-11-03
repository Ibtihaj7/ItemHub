package com.example.itemhub.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.CustomPostBinding
import com.example.itemhub.model.Post
import com.example.itemhub.ui.main.PostDetailListener

class PostItemAdapter(
    private val context: Context,
    private var posts: List<Post>,
    private val favoriteChangeListener: FavoriteChangeListener,
    private val postDetailListener : PostDetailListener
) : ListAdapter<Post, PostItemViewHolder>(DiffCallback()) {
    fun updateData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CustomPostBinding.inflate(layoutInflater, parent, false)
        return PostItemViewHolder(binding, context, favoriteChangeListener,postDetailListener)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val currentPost = posts[position]
        holder.bind(currentPost)
    }

    class DiffCallback:DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.getId() == newItem.getId()

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}