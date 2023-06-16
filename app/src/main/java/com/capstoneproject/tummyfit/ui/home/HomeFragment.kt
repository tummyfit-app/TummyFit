package com.capstoneproject.tummyfit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.FoodsItem
import com.capstoneproject.tummyfit.data.remote.model.food.MealItem
import com.capstoneproject.tummyfit.data.remote.model.user.UserDescription
import com.capstoneproject.tummyfit.databinding.FragmentHomeBinding
import com.capstoneproject.tummyfit.ui.home.adapter.TodayMealAdapter
import com.capstoneproject.tummyfit.ui.home.adapter.TryItAdapter
import com.capstoneproject.tummyfit.utils.scoreIbm
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var tryItAdapter: TryItAdapter
    private lateinit var todayMealAdapter: TodayMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTryItList()
        initTodayMealList()
        observeData()
        toFavorite()
        toWaterIntake()
        toRecipe()
    }

    private fun toRecipe(){
        binding.listMenu.recipe.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_recipeFragment)
        }
    }

    private fun toWaterIntake() {
        binding.listMenu.waterIntake.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_waterIntakeFragment)
        }
    }

    private fun toFavorite() {
        binding.listMenu.favorite.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_favoriteMealsFragment)
        }
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    binding.cardData.totalKcal.text = it.data?.data?.calorie.toString()
                    it.data?.data?.userDescription?.let { it1 ->
                        bindToView(it1)
                    }
                }

                is Resource.Error -> {
                    showLoading(true)
                    if (it.message.equals("No Data found", true)) {
                        if (findNavController().currentDestination?.id == R.id.homeFragment)
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileSetupBottomSheetDialogFragment())
                    } else {
                        showSnackbar(requireView(), it.message.toString())
                    }
                }
            }
        }
        viewModel.foodPredict.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listTodayMeals.shimmerTodayMeals.isVisible = true
                    binding.listTodayMeals.rvTodayMeals.isVisible = false
                }

                is Resource.Empty -> {}

                is Resource.Success -> {
                    binding.listTodayMeals.shimmerTodayMeals.isVisible = false
                    binding.listTodayMeals.rvTodayMeals.isVisible = true
                    todayMealAdapter.differ.submitList(it.data?.data?.meal)
                }

                is Resource.Error -> {
                    binding.listTodayMeals.shimmerTodayMeals.isVisible = true
                    binding.listTodayMeals.rvTodayMeals.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.foodTryIt.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listTryIt.shimmerTryIt.isVisible = true
                    binding.listTryIt.rvTryIt.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listTryIt.shimmerTryIt.isVisible = true
                    binding.listTryIt.rvTryIt.isVisible = false
                }

                is Resource.Success -> {
                    binding.listTryIt.shimmerTryIt.isVisible = false
                    binding.listTryIt.rvTryIt.isVisible = true
                    tryItAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listTryIt.shimmerTryIt.isVisible = true
                    binding.listTryIt.rvTryIt.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.apply {
            shimmerHomeHeader.isVisible = boolean
            shimmerCardData.isVisible = boolean
            cardData.cardData.isVisible = !boolean
            homeHeader.homeHeader.isVisible = !boolean
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
        viewModel.getFoodsTryIt()
        viewModel.getFoodPredict()
    }

    private fun bindToView(userDescription: UserDescription) {
        with(binding) {
            homeHeader.apply {
                Glide.with(pictUser).load(userDescription.user.urlprofile).into(pictUser)
                username.text = "${userDescription.user.firstname} ${userDescription.user.lastname}"
                email.text = userDescription.user.email
            }
            cardData.apply {
                resultHeight.text =
                    String.format(resources.getString(R.string.cm_template), userDescription.height)
                resultWeight.text =
                    String.format(resources.getString(R.string.kg_template), userDescription.weight)
                resultIbm.text = scoreIbm(
                    userDescription.weight,
                    userDescription.height
                ).toString()
            }
        }
    }

    private fun initTryItList() {
        tryItAdapter = TryItAdapter()
        binding.listTryIt.rvTryIt.apply {
            adapter = tryItAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        tryItAdapter.setOnClickListener(object : TryItAdapter.OnItemClickListener {
            override fun onItemClicked(item: FoodsItem) {
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToDetailMealFragment(item.id)
                findNavController().navigate(directions)
            }
        })
    }

    private fun initTodayMealList() {
        todayMealAdapter = TodayMealAdapter()
        binding.listTodayMeals.rvTodayMeals.apply {
            adapter = todayMealAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        todayMealAdapter.setOnClickListener(object : TodayMealAdapter.OnItemClickListener {
            override fun onItemClicked(item: MealItem) {
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToDetailMealFragment(item.foodName)
                findNavController().navigate(directions)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}