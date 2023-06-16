package com.capstoneproject.tummyfit.ui.mealsrecommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.tummyfit.data.remote.model.food.MealItem
import com.capstoneproject.tummyfit.databinding.FragmentMealsRecommendBinding
import com.capstoneproject.tummyfit.ui.mealsrecommend.adapter.MealsAdapter
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
        viewModel.getFoodPredict(getDayFormat())
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
                    mealsAdapter.differ.submitList(it.data?.data?.meal)
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
            override fun onItemClicked(item: MealItem) {
                val directions = MealsRecommendFragmentDirections.actionMealsRecommendFragmentToDetailMealFragment(item.foodName)
                findNavController().navigate(directions)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}