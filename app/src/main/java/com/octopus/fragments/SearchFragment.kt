package com.octopus.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.octopus.R
import com.octopus.databinding.FragmentSearchBinding
import com.octopus.domain.OctConstants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var navController: NavController
    private var queryString = OctConstants.EMPTY_STRING
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVIew()
    }

    private fun initVIew() {
        binding.searchView.isIconifiedByDefault = false
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    queryString = query
                }
                searchProfile(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.searchBtn.visibility = View.GONE
                } else {
                    binding.searchBtn.visibility = View.VISIBLE
                }
                return false
            }
        })

        binding.searchBtn.setOnClickListener {
            searchProfile(binding.searchView.query.toString())
        }
    }

    private fun searchProfile(query:String) {
        val bundle = bundleOf(OctConstants.USERNAME to query)
        navController.navigate(R.id.action_searchFragment2_to_profileFragment,bundle)
    }
}

