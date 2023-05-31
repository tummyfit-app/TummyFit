package com.capstoneproject.tummyfit.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.databinding.FragmentFavoriteMealsBinding
import com.capstoneproject.tummyfit.databinding.FragmentOnBoardingBinding
import com.capstoneproject.tummyfit.ui.favorite.adapter.FavoriteAdapter
import com.capstoneproject.tummyfit.ui.home.HomeFragmentDirections
import com.capstoneproject.tummyfit.ui.home.adapter.TryItAdapter
import com.capstoneproject.tummyfit.ui.onboarding.OnBoardingViewModel
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMealsFragment : Fragment() {

    private var _binding: FragmentFavoriteMealsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteMealsViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMealsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllFavorites()
        initFavoriteList()
        observeData()
        back()
    }

    private fun observeData() {
        viewModel.favorite.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listMealsRecommend.apply {
                        rvTodayMeals.isVisible = false
                        shimmerTodayMeals.isVisible = true
                        emptyFavorite.isVisible = false
                    }
                }

                is Resource.Error -> {
                    binding.listMealsRecommend.apply {
                        rvTodayMeals.isVisible = false
                        shimmerTodayMeals.isVisible = false
                        emptyFavorite.isVisible = false
                    }
                    showSnackbar(requireView(), it.message.toString())
                }

                is Resource.Success -> {
                    binding.listMealsRecommend.apply {
                        rvTodayMeals.isVisible = true
                        shimmerTodayMeals.isVisible = false
                        emptyFavorite.isVisible = false
                    }
                    favoriteAdapter.differ.submitList(it.data)
                }

                is Resource.Empty -> {
                    binding.listMealsRecommend.apply {
                        rvTodayMeals.isVisible = false
                        shimmerTodayMeals.isVisible = false
                        emptyFavorite.isVisible = true
                    }
                }
            }
        }
    }

    private fun initFavoriteList() {
        favoriteAdapter = FavoriteAdapter()
        binding.listMealsRecommend.rvTodayMeals.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        favoriteAdapter.setOnClickListener(object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClicked(item: FavoriteMealEntity) {
                val directions = FavoriteMealsFragmentDirections.actionFavoriteMealsFragmentToDetailMealFragment(item.id_meal)
                findNavController().navigate(directions)
            }
        })
    }

    private fun back() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}