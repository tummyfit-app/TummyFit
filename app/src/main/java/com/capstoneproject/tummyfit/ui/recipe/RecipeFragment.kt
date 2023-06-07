package com.capstoneproject.tummyfit.ui.recipe

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
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.databinding.FragmentRecipeBinding
import com.capstoneproject.tummyfit.databinding.FragmentWaterIntakeBinding
import com.capstoneproject.tummyfit.ui.home.HomeFragmentDirections
import com.capstoneproject.tummyfit.ui.home.adapter.TodayMealAdapter
import com.capstoneproject.tummyfit.ui.home.adapter.TryItAdapter
import com.capstoneproject.tummyfit.ui.recipe.adapter.CheapAdapter
import com.capstoneproject.tummyfit.ui.recipe.adapter.FastFoodAdapter
import com.capstoneproject.tummyfit.ui.recipe.adapter.HalalAdapter
import com.capstoneproject.tummyfit.ui.recipe.adapter.PopularAdapter
import com.capstoneproject.tummyfit.ui.waterintake.WaterIntakeViewModel
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var halalAdapter: HalalAdapter
    private lateinit var cheapAdapter: CheapAdapter
    private lateinit var fastFoodAdapter: FastFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toBack()
        initTodayMealList()
        observeData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFoodPopular()
        viewModel.getFoodHalal()
        viewModel.getFoodCheap()
        viewModel.getFoodFast()
    }

    private fun observeData() {
        viewModel.popular.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listPopularMeals.shimmerTodayMeals.isVisible = true
                    binding.listPopularMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listPopularMeals.shimmerTodayMeals.isVisible = true
                    binding.listPopularMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Success -> {
                    binding.listPopularMeals.shimmerTodayMeals.isVisible = false
                    binding.listPopularMeals.rvTodayMeals.isVisible = true
                    popularAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listPopularMeals.shimmerTodayMeals.isVisible = true
                    binding.listPopularMeals.rvTodayMeals.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.cheap.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listCheapMeals.shimmerTodayMeals.isVisible = true
                    binding.listCheapMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listCheapMeals.shimmerTodayMeals.isVisible = true
                    binding.listCheapMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Success -> {
                    binding.listCheapMeals.shimmerTodayMeals.isVisible = false
                    binding.listCheapMeals.rvTodayMeals.isVisible = true
                    cheapAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listCheapMeals.shimmerTodayMeals.isVisible = true
                    binding.listCheapMeals.rvTodayMeals.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.halal.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listHalalMeals.shimmerTodayMeals.isVisible = true
                    binding.listHalalMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listHalalMeals.shimmerTodayMeals.isVisible = true
                    binding.listHalalMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Success -> {
                    binding.listHalalMeals.shimmerTodayMeals.isVisible = false
                    binding.listHalalMeals.rvTodayMeals.isVisible = true
                    halalAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listHalalMeals.shimmerTodayMeals.isVisible = true
                    binding.listHalalMeals.rvTodayMeals.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.fastFood.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listFastMeals.shimmerTodayMeals.isVisible = true
                    binding.listFastMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listFastMeals.shimmerTodayMeals.isVisible = true
                    binding.listFastMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Success -> {
                    binding.listFastMeals.shimmerTodayMeals.isVisible = false
                    binding.listFastMeals.rvTodayMeals.isVisible = true
                    fastFoodAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listFastMeals.shimmerTodayMeals.isVisible = true
                    binding.listFastMeals.rvTodayMeals.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun initTodayMealList() {
        popularAdapter = PopularAdapter()
        fastFoodAdapter = FastFoodAdapter()
        cheapAdapter = CheapAdapter()
        halalAdapter = HalalAdapter()
        with(binding) {
            listPopularMeals.rvTodayMeals.apply {
                adapter = popularAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            listHalalMeals.rvTodayMeals.apply {
                adapter = halalAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            listCheapMeals.rvTodayMeals.apply {
                adapter = cheapAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            listFastMeals.rvTodayMeals.apply {
                adapter = fastFoodAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

        popularAdapter.setOnClickListener(object : PopularAdapter.OnItemClickListener {
            override fun onItemClicked(item: FoodsItem) {
                val directions = RecipeFragmentDirections.actionRecipeFragmentToDetailMealFragment(item.id)
                findNavController().navigate(directions)
            }
        })

        cheapAdapter.setOnClickListener(object : CheapAdapter.OnItemClickListener {
            override fun onItemClicked(item: FoodsItem) {
                val directions = RecipeFragmentDirections.actionRecipeFragmentToDetailMealFragment(item.id)
                findNavController().navigate(directions)
            }
        })

        fastFoodAdapter.setOnClickListener(object : FastFoodAdapter.OnItemClickListener {
            override fun onItemClicked(item: FoodsItem) {
                val directions = RecipeFragmentDirections.actionRecipeFragmentToDetailMealFragment(item.id)
                findNavController().navigate(directions)
            }
        })

        halalAdapter.setOnClickListener(object : HalalAdapter.OnItemClickListener {
            override fun onItemClicked(item: FoodsItem) {
                val directions = RecipeFragmentDirections.actionRecipeFragmentToDetailMealFragment(item.id)
                findNavController().navigate(directions)
            }
        })
    }

    private fun toBack() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}