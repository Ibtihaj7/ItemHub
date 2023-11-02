package com.example.itemhub.ui.main.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itemhub.R
import com.example.itemhub.databinding.CustomPostBinding
import com.example.itemhub.model.Post
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.google.gson.Gson

class PostItemViewHolder(
    private val binding: CustomPostBinding,
    private val context: Context,
    private val favoriteChangeListener: FavoriteChangeListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        bindPostInfo(post)
        setupFavoriteButton(post)
        setupShareButton(post)
        loadPostImage(post)
    }

    private fun bindPostInfo(post: Post) {
        binding.postInfo = post
    }

    private fun setupFavoriteButton(post: Post) {
        val heartBtnResource = if (post.getFavoriteState()) R.drawable.fill_heart else R.drawable.empty_heart
        binding.heartBtn.setImageResource(heartBtnResource)

        binding.heartBtn.setOnClickListener {
            post.setFavoriteState(!post.getFavoriteState())
            favoriteChangeListener.onFavoriteChanged(post)
        }
    }

    private fun setupShareButton(post: Post) {
        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = MIME_TYPE_TEXT_PLAIN

            val gson = Gson()
            val json = gson.toJson(post)
            intent.putExtra(EXTRA_POST_INFORMATION, json)

            context.startActivity(intent)
        }
    }

    private fun loadPostImage(post: Post) {
        Glide.with(context)
            .load(post.getPhoto())
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .error(R.drawable.error_img)
            .circleCrop()
            .into(binding.imageView)
    }

    companion object {
        private const val EXTRA_POST_INFORMATION = "post_information"
        private const val MIME_TYPE_TEXT_PLAIN = "text/plain"
    }
}