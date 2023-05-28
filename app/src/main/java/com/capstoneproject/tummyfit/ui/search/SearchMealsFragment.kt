package com.capstoneproject.tummyfit.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.databinding.FragmentProfileBinding
import com.capstoneproject.tummyfit.databinding.FragmentSearchMealsBinding
import com.capstoneproject.tummyfit.ui.home.adapter.TryItAdapter
import com.capstoneproject.tummyfit.ui.profile.ProfileViewModel
import com.capstoneproject.tummyfit.ui.search.adapter.SearchAdapter
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMealsFragment : Fragment() {

    private var _binding: FragmentSearchMealsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchMealsViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMealsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchList()
        observeData()
    }

    private fun observeData() {
        viewModel.search.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listSearch.shimmerSearch.isVisible = true
                    binding.listSearch.rvPerfectMatch.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listSearch.shimmerSearch.isVisible = false
                    binding.listSearch.rvPerfectMatch.isVisible = false
                }

                is Resource.Success -> {
                    binding.listSearch.shimmerSearch.isVisible = false
                    binding.listSearch.rvPerfectMatch.isVisible = true
                    searchAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listSearch.shimmerSearch.isVisible = true
                    binding.listSearch.rvPerfectMatch.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun initSearchList() {
        searchAdapter = SearchAdapter()
        binding.listSearch.rvPerfectMatch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}