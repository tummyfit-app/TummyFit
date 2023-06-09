package com.capstoneproject.tummyfit.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.databinding.FragmentSearchMealsBinding
import com.capstoneproject.tummyfit.ui.search.adapter.SearchAdapter
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMealsFragment : Fragment() {

    private var _binding: FragmentSearchMealsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchMealsViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private var category: String? = null

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
        searchFoods()
        selectedCategory()
    }

    private fun selectedCategory() {
        binding.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = group.findViewById(checkedId)
            if (chip != null) {
                category = chip.text.toString()
                viewModel.searchFoodsByCategory(category = category)
            } else {
                category = null
            }
        }
    }

    private fun searchFoods() {
        binding.apply {
            cardSearch.searchView.setIconifiedByDefault(false)
            cardSearch.searchView.queryHint = resources.getString(R.string.search_meals)
            cardSearch.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (category != null){
                            viewModel.searchFoodsByCategory(
                                it.replaceFirstChar { char -> char.uppercase() },
                                category
                            )
                        }else {
                            viewModel.searchFoodsByCategory(it.replaceFirstChar { char -> char.uppercase() })
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.length == 0) {
                        cardSearch.searchView.clearFocus()
                    }
                    return true
                }
            })
        }
    }

    private fun observeData() {
        viewModel.search.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listSearch.shimmerSearch.isVisible = true
                    binding.listSearch.rvPerfectMatch.isVisible = false
                    binding.notFoundAnimation.isVisible = false
                    binding.tvNotFound.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listSearch.shimmerSearch.isVisible = false
                    binding.listSearch.rvPerfectMatch.isVisible = false
                    binding.notFoundAnimation.isVisible = true
                    binding.tvNotFound.isVisible = true
                }

                is Resource.Success -> {
                    binding.listSearch.shimmerSearch.isVisible = false
                    binding.listSearch.rvPerfectMatch.isVisible = true
                    binding.notFoundAnimation.isVisible = false
                    binding.tvNotFound.isVisible = false
                    searchAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listSearch.shimmerSearch.isVisible = true
                    binding.listSearch.rvPerfectMatch.isVisible = false
                    binding.notFoundAnimation.isVisible = false
                    binding.tvNotFound.isVisible = false
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

        searchAdapter.setOnClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClicked(item: FoodsItem) {
                val directions =
                    SearchMealsFragmentDirections.actionSearchMealsFragmentToDetailMealFragment(item.id)
                findNavController().navigate(directions)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}