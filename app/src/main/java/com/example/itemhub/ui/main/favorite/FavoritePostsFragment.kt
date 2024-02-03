package com.example.itemhub.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itemhub.ui.main.FavoriteChangeListener
import com.example.itemhub.databinding.FragmentFavoriteContactsBinding
import com.example.itemhub.model.Post
import com.example.itemhub.model.PostItem
import com.example.itemhub.ui.main.MainViewModel
import com.example.itemhub.ui.main.PostDetailListener
import com.example.itemhub.ui.main.adapter.PostItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePostsFragment : Fragment(), FavoriteChangeListener, PostDetailListener {
    private lateinit var binding: FragmentFavoriteContactsBinding
    private lateinit var favoritesPostsAdapter: PostItemAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var homeNavController: NavController

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteContactsBinding.inflate(inflater)
        emptyView = binding.emptyView
        homeNavController = findNavController()

        setupRecyclerView()
        observeFavorites()

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerview = binding.favoritePostsRv
        recyclerview.layoutManager = LinearLayoutManager(activity)
        favoritesPostsAdapter = PostItemAdapter(requireContext(), this,this)
        recyclerview.adapter = favoritesPostsAdapter
    }

    private fun observeFavorites() {
        sharedViewModel.favoritesLiveData.observe(viewLifecycleOwner) {
            favoritesPostsAdapter.updateData(it)
            emptyView.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onFavoriteChanged(post: PostItem) {
        sharedViewModel.onFavoriteChanged(post)
    }

    override fun onCardViewClicked(post: PostItem) {
        val action = FavoritePostsFragmentDirections.actionFavoriteContactsToPostDetailFragment2(post.getId())
        homeNavController.navigate(action)
    }
}