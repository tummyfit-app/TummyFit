package com.capstoneproject.tummyfit.ui.mealsrecommend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.MenuItem
import com.capstoneproject.tummyfit.databinding.FragmentMealsRecommendBinding
import com.capstoneproject.tummyfit.databinding.FragmentProfileBinding
import com.capstoneproject.tummyfit.ui.home.HomeFragmentDirections
import com.capstoneproject.tummyfit.ui.home.adapter.TodayMealAdapter
import com.capstoneproject.tummyfit.ui.mealsrecommend.adapter.MealsAdapter
import com.capstoneproject.tummyfit.ui.profile.ProfileViewModel
import com.capstoneproject.tummyfit.utils.getChipDayFormat
import com.capstoneproject.tummyfit.utils.getDayFormat
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealsRecommendFragment : Fragment() {

    private var _binding: FragmentMealsRecommendBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MealsRecommendViewModel by viewModels()
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealsRecommendBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initTodayMealList()
        setUpChip()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFoodPredict()
    }

    private fun setUpChip() {
        with(binding.chipGroupDay) {
            for (i in 0 until childCount) {
                val chip = getChildAt(i) as Chip
                if (chip.text.toString() == getChipDayFormat()) {
                    check(chip.id)
                    chip.isChecked = chip.text.toString() == getChipDayFormat()
                }
                chip.isCheckable = false
                chip.isClickable = false
            }
        }
    }

    private fun observeData() {
        viewModel.foodPredict.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listMealsRecommend.rvTodayMeals.isVisible = false
                    binding.listMealsRecommend.shimmerTodayMeals.isVisible = true
                }

                is Resource.Empty -> {
                    binding.listMealsRecommend.rvTodayMeals.isVisible = false
                    binding.listMealsRecommend.shimmerTodayMeals.isVisible = true
                }

                is Resource.Success -> {
                    binding.listMealsRecommend.rvTodayMeals.isVisible = true
                    binding.listMealsRecommend.shimmerTodayMeals.isVisible = false
                    mealsAdapter.differ.submitList(it.data?.prediction?.get(getDayFormat())?.menu)
                }

                is Resource.Error -> {
                    binding.listMealsRecommend.rvTodayMeals.isVisible = false
                    binding.listMealsRecommend.shimmerTodayMeals.isVisible = true
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun initTodayMealList() {
        mealsAdapter = MealsAdapter()
        binding.listMealsRecommend.rvTodayMeals.apply {
            adapter = mealsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        mealsAdapter.setOnClickListener(object : MealsAdapter.OnItemClickListener {
            override fun onItemClicked(item: MenuItem) {
                val directions = MealsRecommendFragmentDirections.actionMealsRecommendFragmentToDetailMealFragment(item.recipeTitle)
                findNavController().navigate(directions)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}