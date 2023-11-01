package com.example.itemhub.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.FragmentFavoriteContactsBinding
import com.example.itemhub.model.Post
import com.example.itemhub.ui.main.SharedViewModel
import com.example.itemhub.ui.adapter.PostItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePostsFragment : Fragment(), FavoriteChangeListener {
    private lateinit var binding: FragmentFavoriteContactsBinding
    private lateinit var favoritesPostsAdapter: PostItemAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerview: RecyclerView
    private val viewModel: FavoritePostsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteContactsBinding.inflate(inflater)
        setupUI()

        sharedViewModel.getFavoritePostsList().observe(viewLifecycleOwner, Observer{ favoritePostsIdList ->
            val newData = viewModel.filterData()
            favoritesPostsAdapter.updateData(newData)
        })

        return binding.root
    }
    private fun setupUI() {
        setupRecyclerView()
        observeSharedViewModel()
    }
    private fun setupRecyclerView() {
        recyclerview = binding.favoritePostsRv
        recyclerview.layoutManager = LinearLayoutManager(activity)
        favoritesPostsAdapter = PostItemAdapter(requireContext(), emptyList(), this)
        recyclerview.adapter = favoritesPostsAdapter
    }

    private fun observeSharedViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.getFavoritePostsList().observe(viewLifecycleOwner) {
            favoritesPostsAdapter.notifyDataSetChanged()
        }
    }

    override fun onFavoriteChanged(post: Post) {
        sharedViewModel.onFavoriteChanged(post)
    }
}