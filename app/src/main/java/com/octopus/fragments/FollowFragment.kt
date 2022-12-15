package com.octopus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.octopus.R
import com.octopus.SearchNewViewModel
import com.octopus.adapters.ItemSelected
import com.octopus.adapters.PagingFollowerAdapter
import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.databinding.FragmentFollowBinding
import com.octopus.domain.OctConstants
import com.octopus.domain.models.Follower
import com.octopus.domain.models.Following
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment(), ItemSelected {
    lateinit var navController: NavController

    lateinit var binding: FragmentFollowBinding
    private val searchNewViewModel: SearchNewViewModel by viewModels()
    lateinit var adapter: PagingFollowerAdapter
    private var username: String = OctConstants.EMPTY_STRING
    private var followType: String = OctConstants.EMPTY_STRING
    private var avatarUrl: String = OctConstants.EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observers()

    }

    private fun initView() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val bundle = arguments
        username = bundle?.getString(OctConstants.USERNAME) ?: OctConstants.EMPTY_STRING
        followType = bundle?.getString(OctConstants.FOLLOW_TYPE) ?: OctConstants.EMPTY_STRING
        avatarUrl = bundle?.getString(OctConstants.AVATAR_URL) ?: OctConstants.EMPTY_STRING

        binding.userFollowRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = PagingFollowerAdapter(requireContext(),this)
        binding.userFollowRecyclerview.adapter = adapter

        Glide
            .with(this)
            .load(avatarUrl)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.octocat)
            .into(binding.userImage)
        binding.usernametv.text = username


        binding.backArrowFollow.setOnClickListener {
            navController.navigateUp()
        }

    }

    private fun observers() {
        if (followType == OctConstants.FOLLOWER) {
            binding.screenTitleTv.text = getString(R.string.current_followers)
            searchNewViewModel.getFollowers(username)
        } else {
            binding.screenTitleTv.text = getString(R.string.current_following)
            searchNewViewModel.getFollowing(username)
        }

        searchNewViewModel.uiState.observe(viewLifecycleOwner) { loadState ->

            when (loadState) {
                is BaseViewModel.LoadingState.ShowLoading -> {
                    binding.loadView.progressBar.visibility = View.VISIBLE
                    binding.loadView.errorView.errorView.visibility = View.GONE

                }
                is BaseViewModel.LoadingState.Error -> {
                    binding.loadView.progressBar.visibility = View.GONE
                    binding.loadView.errorView.errorView.visibility = View.VISIBLE
                    binding.loadView.errorView.errorMessageTv.text = loadState.message
                }
                is BaseViewModel.LoadingState.Success -> {
                    loadState.data?.let { displayData(it) }
                    binding.loadView.root.visibility= View.GONE
                    binding.loadView.errorView.errorView.visibility = View.GONE
                }
                is BaseViewModel.LoadingState.Default -> {
                //    binding.overlay.visibility=View.GONE
                    binding.loadView.progressBar.visibility = View.GONE
                    binding.loadView.errorView.errorView.visibility = View.GONE

                }
                else -> {}
            }
        }
    }

    private fun displayData(data: Any) {
        when (data) {
            is BaseViewModel.PageDataType-> {
                displayFollowers(data.pageData)
            }
        }
    }

    private fun displayFollowers(followers: PagingData<*>) {
        adapter.submitData(lifecycle, followers as PagingData<Any>)
    }


    override fun onSelected(item: Any) {
        when (item) {
            is Follower -> {
                navigateToUserProfile(item.login)
            }

            is Following -> {
                navigateToUserProfile(item.login)
            }
        }
    }

    private fun navigateToUserProfile(username: String) {
        val bundle = bundleOf(OctConstants.USERNAME to username)
        navController.navigate(R.id.action_followFragment_to_profileFragment, bundle)
    }

}