package com.example.itemhub

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.itemhub.databinding.FragmentPostDetailBinding
import com.example.itemhub.model.Post


class PostDetailFragment : Fragment() {
    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDetailBinding.inflate(inflater)

        val id = arguments?.getInt("id")
        val userId = arguments?.getInt("userId")
        val title = arguments?.getString("title")
        val body = arguments?.getString("body")
        val photo = arguments?.getString("photo")

        Log.d("main","id : ${id} - title : $title")

        val post:Post = Post(userId!!,id!!,title!!,body!!)
        binding.post = post

        Glide.with(requireContext())
            .load(post.getPhoto())
            .centerCrop()
            .error(R.drawable.stat_notify_error)
            .into(binding.photoImageView)


        return binding.root
    }
}