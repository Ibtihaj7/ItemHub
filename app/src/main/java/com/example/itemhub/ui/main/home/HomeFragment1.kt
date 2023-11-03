package com.example.itemhub.ui.main.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemhub.databinding.FragmentHome1Binding
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.model.Post
import com.example.itemhub.ui.main.MainViewModel
import com.example.itemhub.ui.main.PostDetailListener
import com.example.itemhub.ui.main.adapter.PostItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment1 : Fragment(), FavoriteChangeListener, PostDetailListener{
    private lateinit var binding: FragmentHome1Binding
    private lateinit var recyclerview: RecyclerView
    private lateinit var allPostsAdapter: PostItemAdapter
    private lateinit var homeNavController: NavController

    private val sharedViewModel: MainViewModel by activityViewModels()

    private var handler:Handler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome1Binding.inflate(inflater)
        homeNavController = findNavController()
        setupUI()
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
        allPostsAdapter = PostItemAdapter(requireContext(), emptyList(), this,this)
        recyclerview.adapter = allPostsAdapter
    }

    private fun observeSharedViewModel() {
        sharedViewModel.postsList.observe(viewLifecycleOwner) {
            allPostsAdapter.updateData(it)

            val noResultsTextView = binding.noResultsTextView
            noResultsTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                handler?.removeCallbacksAndMessages(null)
                handler = Handler(Looper.getMainLooper())
                handler?.postDelayed({ searchPosts(newText) }, FILTER_DELAY)
                return true
            }
        })
    }

    private fun searchPosts(query: String?) {
        sharedViewModel.filterList(query)
    }

    override fun onFavoriteChanged(post: Post) {
        sharedViewModel.onFavoriteChanged(post)
    }

    companion object{
        private const val FILTER_DELAY : Long = 500
    }

    override fun onCardViewClicked(post: Post) {
        val action = HomeFragment1Directions.actionHomeFragment1ToPostDetailFragment()
        homeNavController.navigate(action)
    }
}