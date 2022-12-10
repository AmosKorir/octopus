package com.octopus.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.octopus.R
import com.octopus.SearchNewViewModel
import com.octopus.adapters.FollowerRecyclerViewAdapter
import com.octopus.adapters.PagingFollowerAdapter
import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.databinding.FragmentFollowBinding
import com.octopus.domain.models.Follower
import com.octopus.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {
    lateinit var binding: FragmentFollowBinding
    val searchNewViewModel: SearchNewViewModel by viewModels<SearchNewViewModel>()
    lateinit var adapter:PagingFollowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
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
        binding.userFollowRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
       adapter =PagingFollowerAdapter(requireContext())
        binding.userFollowRecyclerview.adapter = adapter


    }

    private fun observers() {

        searchNewViewModel.getPaginatedFollowers("amoskorir")
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

            is java.util.ArrayList<*> -> {
               // displayFollowers(data as ArrayList<Follower>)
            }

            is PagingData<*>->{
                displayFollowers(data as PagingData<Follower>)
            }
        }
    }

    private fun displayFollowers(followers: PagingData<Follower>) {
       adapter.submitData(lifecycle,followers)

    }

}