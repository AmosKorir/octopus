package com.octopus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.octopus.R
import com.octopus.SearchNewViewModel
import com.octopus.adapters.FollowerRecyclerViewAdapter
import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.databinding.FragmentProfileBinding
import com.octopus.domain.models.Follower
import com.octopus.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val searchNewViewModel: SearchNewViewModel by viewModels<SearchNewViewModel>()
    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        initView()
    }

    private fun initView() {
        binding.followerLayout.followersRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followingLayout.followersRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observers() {

        searchNewViewModel.searchUser("amoskorir")
        searchNewViewModel.uiState.observe(viewLifecycleOwner) { loadState ->

            when (loadState) {
                is BaseViewModel.LoadingState.ShowLoading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()

                }
                is BaseViewModel.LoadingState.Error -> {
                    Toast.makeText(requireContext(), loadState.message, Toast.LENGTH_SHORT).show()
                }
                is BaseViewModel.LoadingState.Success -> {
                    loadState.data?.let { displayData(it) }

                }
                is BaseViewModel.LoadingState.Default -> {

                }
                else -> {}
            }
        }
    }

    private fun displayData(data: Any) {
        when (data) {
            is User -> {
                showUserDetails(data)
            }

            is java.util.ArrayList<*> -> {
               displayFollowers(data as ArrayList<Follower>)
               displayFollowing(data as ArrayList<Follower>)
            }
        }
    }

    private fun showUserDetails(user: User) {
        binding.usernameTv.text = user.name
        binding.organizationTv.text = user.company
        searchNewViewModel.getFollowers("amoskorir")

        Glide
            .with(this)
            .load(user.avatar_url)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.octocat)
            .into(binding.userImageView);


        binding.biolayout.bioTv.text = user.bio
    }

    private fun displayFollowers(followers: ArrayList<Follower>) {
        val adapter = FollowerRecyclerViewAdapter(requireContext(), followers)
        binding.followerLayout.followersRecyclerview.adapter = adapter
    }

    private fun displayFollowing(followers: ArrayList<Follower>) {
        val adapter = FollowerRecyclerViewAdapter(requireContext(), followers)
        binding.followingLayout.followersRecyclerview.adapter = adapter
    }
}