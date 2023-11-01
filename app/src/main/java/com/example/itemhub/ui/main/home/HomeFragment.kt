package com.example.itemhub.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.FragmentHomeBinding
import com.example.itemhub.model.Post
import com.example.itemhub.ui.main.SharedViewModel
import com.example.itemhub.ui.adapter.PostItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), FavoriteChangeListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var allPostsAdapter: PostItemAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var handler:Handler
    companion object{
        private const val FILTER_DELAY : Long = 500
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        setupUI()

        sharedViewModel.getPostsList().observe(viewLifecycleOwner, Observer{ favoriteUserList ->
            allPostsAdapter.updateData(favoriteUserList)
        })

        return binding.root
    }
    private fun setupUI() {
        setupRecyclerView()
        setupSearchView()
        observeSharedViewModel()
    }

    private fun setupRecyclerView() {
        recyclerview = binding.homeRv
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        allPostsAdapter = PostItemAdapter(requireContext(), emptyList(), this)
        recyclerview.adapter = allPostsAdapter
    }
    private fun observeSharedViewModel() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.getFavoritePostsList().observe(viewLifecycleOwner) {
            allPostsAdapter.notifyDataSetChanged()
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({ filter(newText) }, FILTER_DELAY)
                return true
            }
        })
    }

    private fun filter(query: String?) {
        val filteredList = viewModel.filterList(query)
        val noResultsTextView = binding.noResultsTextView
        noResultsTextView.visibility = if (filteredList.isEmpty()) View.VISIBLE else View.GONE

        allPostsAdapter.updateData(filteredList)
    }

    override fun onFavoriteChanged(post: Post) {
        sharedViewModel.onFavoriteChanged(post)
    }
}