package com.octopus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.octopus.R
import com.octopus.SearchNewViewModel
import com.octopus.adapters.HorizontalPagingFollowerAdapter
import com.octopus.adapters.ItemSelected
import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.databinding.FragmentProfileBinding
import com.octopus.domain.OctConstants
import com.octopus.domain.models.Follower
import com.octopus.domain.models.Following
import com.octopus.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), ItemSelected {
    private lateinit var navController: NavController
    private val searchNewViewModel: SearchNewViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    lateinit var followerAdapter: HorizontalPagingFollowerAdapter
    lateinit var followingAdapter: HorizontalPagingFollowerAdapter

    var username: String = OctConstants.EMPTY_STRING
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observers()
    }

    private fun initView() {
        val bundle = arguments
        username = bundle?.getString(OctConstants.USERNAME) ?: OctConstants.EMPTY_STRING
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding.followerLayout.followersRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.followingLayout.followersRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        followerAdapter = HorizontalPagingFollowerAdapter(requireContext(), this)
        followingAdapter = HorizontalPagingFollowerAdapter(requireContext(), this)



        binding.backArrow.setOnClickListener {
            navController.navigateUp()
        }

        binding.followerLayout.followerMore.setOnClickListener {
            navigateToMoreFollows(OctConstants.FOLLOWER)
        }
        binding.followingLayout.followingMore.setOnClickListener {
            navigateToMoreFollows(OctConstants.FOLLOWING)
        }
    }


    private fun observers() {

        searchNewViewModel.searchUser(username)
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
                    binding.loadView.progressBar.visibility = View.GONE
                    binding.loadView.errorView.errorView.visibility = View.GONE

                }
                is BaseViewModel.LoadingState.Default -> {
                    binding.loadView.progressBar.visibility = View.GONE
                    binding.loadView.errorView.errorView.visibility = View.GONE

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
            is BaseViewModel.PageDataType -> {
                if (data.type == OctConstants.FOLLOWER) {
                    displayFollowers(data.pageData as PagingData<Any>)
                } else if (data.type == OctConstants.FOLLOWING) {
                    displayFollowing(data.pageData as PagingData<Any>)

                }

            }
        }
    }

    private fun showUserDetails(user: User) {
        this.user = user
        binding.usernameTv.text = user.name
        binding.organizationTv.text = user.company

        binding.userTotalVIew.followersChip.text =getString(R.string.followers,user.followers.toString())
        binding.userTotalVIew.followingChip.text =getString(R.string.followings,user.following.toString())
        binding.userTotalVIew.repositoriesChip.text = getString(R.string.repositories,user.public_repos.toString())
        searchNewViewModel.getFollowers(username)
        searchNewViewModel.getFollowing(username)


        Glide.with(this).load(user.avatar_url).centerCrop().circleCrop()
            .placeholder(R.drawable.octocat).into(binding.userImageView);

        binding.biolayout.bioTv.text = user.bio
    }

    private fun displayFollowers(followers: PagingData<Any>) {
        followerAdapter.submitData(lifecycle, followers)
        binding.followerLayout.followersRecyclerview.adapter = followerAdapter
    }

    private fun displayFollowing(following: PagingData<Any>) {
        followingAdapter.submitData(lifecycle, following)
        binding.followingLayout.followersRecyclerview.adapter = followingAdapter
    }


    private fun navigateToMoreFollows(followerType: String) {
        val bundle = bundleOf(
            OctConstants.USERNAME to username,
            OctConstants.AVATAR_URL to user?.avatar_url,
            OctConstants.FOLLOW_TYPE to followerType
        )
        navController.navigate(R.id.action_profileFragment_to_followFragment, bundle)
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
        navController.navigate(R.id.action_profileFragment_self2, bundle)
    }
}