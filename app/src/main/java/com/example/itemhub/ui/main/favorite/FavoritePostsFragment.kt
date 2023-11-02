package com.example.itemhub.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.FragmentFavoriteContactsBinding
import com.example.itemhub.model.Post
import com.example.itemhub.ui.main.MainViewModel
import com.example.itemhub.ui.main.adapter.PostItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePostsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var binding: FragmentFavoriteContactsBinding
    private lateinit var favoritesPostsAdapter: PostItemAdapter
    private lateinit var recyclerview: RecyclerView

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteContactsBinding.inflate(inflater)
        setupUI()
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        sharedViewModel.favoritesLiveData.observe(viewLifecycleOwner) {
            favoritesPostsAdapter.updateData(it)
        }
    }

    private fun setupUI() {
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        recyclerview = binding.favoritePostsRv
        recyclerview.layoutManager = LinearLayoutManager(activity)
        favoritesPostsAdapter = PostItemAdapter(requireContext(), emptyList(), this)
        recyclerview.adapter = favoritesPostsAdapter
    }

    override fun onFavoriteChanged(post: Post) {
        sharedViewModel.onFavoriteChanged(post)
    }
}