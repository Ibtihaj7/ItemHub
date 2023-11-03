package com.example.itemhub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itemhub.model.Post

class PostDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val post: Post? = arguments?.getString("postArg") as? Post
//
//        Log.d("main","post : ${post?.getTitle()}")
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }
}