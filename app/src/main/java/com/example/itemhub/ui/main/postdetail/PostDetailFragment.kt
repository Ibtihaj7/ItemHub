package com.example.itemhub.ui.main.postdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.itemhub.R
import com.example.itemhub.databinding.FragmentPostDetailBinding
import com.example.itemhub.model.Post
import com.example.itemhub.model.PostItem
import com.example.itemhub.ui.main.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private val viewModel: PostDetailViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var gson:Gson

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater)

        val postId = arguments?.getInt("postId") ?: 0
        val post = viewModel.getPost(postId)
        viewModel.setPost(post)

        setupPostViews(post)
        setupFavoriteButton(post)
        setupShareButton(post)
        observePost()

        return binding.root
    }

    private fun setupPostViews(post: PostItem) {
        Glide.with(requireContext())
            .load(post.getPhoto())
            .centerCrop()
            .error(R.drawable.error_img)
            .into(binding.photoImageView)
    }

    private fun setupFavoriteButton(post: PostItem) {
        binding.heartBtn.setOnClickListener {
            sharedViewModel.onFavoriteChanged(post)
            viewModel.setPost(post)
        }
    }

    private fun setupShareButton(post: PostItem) {
        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = MIME_TYPE_TEXT_PLAIN

            val json = gson.toJson(post)
            intent.putExtra(EXTRA_POST_INFORMATION, json)

            context?.startActivity(intent)
        }
    }

    private fun observePost() {
        viewModel.post.observe(viewLifecycleOwner) { post ->
            binding.post = post
            val heartBtnResource = if (post.getFavoriteState()) R.drawable.fill_heart else R.drawable.empty_heart
            binding.heartBtn.setImageResource(heartBtnResource)
        }
    }

    companion object {
        private const val EXTRA_POST_INFORMATION = "post_information"
        private const val MIME_TYPE_TEXT_PLAIN = "text/plain"
    }
}