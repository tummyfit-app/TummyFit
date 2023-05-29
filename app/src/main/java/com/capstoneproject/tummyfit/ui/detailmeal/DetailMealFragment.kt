package com.capstoneproject.tummyfit.ui.detailmeal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.DataFood
import com.capstoneproject.tummyfit.data.remote.model.food.Food
import com.capstoneproject.tummyfit.databinding.FragmentDetailMealBinding
import com.capstoneproject.tummyfit.databinding.FragmentSearchMealsBinding
import com.capstoneproject.tummyfit.ui.detailmeal.adapter.SectionsPagerAdapter
import com.capstoneproject.tummyfit.ui.search.SearchMealsViewModel
import com.capstoneproject.tummyfit.ui.search.adapter.SearchAdapter
import com.capstoneproject.tummyfit.utils.Constants
import com.capstoneproject.tummyfit.wrapper.Resource
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class DetailMealFragment : Fragment() {

    private var _binding: FragmentDetailMealBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailMealViewModel by viewModels()
    private val navArgs: DetailMealFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailFood(navArgs.id.toString())
        observeData()
        back()
    }

    private fun observeData() {
        viewModel.detail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Error -> {
                }

                is Resource.Success -> {
                    it.data?.data?.food?.let { it1 -> bindToView(it1) }
                }

                is Resource.Empty -> {

                }
            }
        }
    }

    private fun bindToView(food: Food) {
        binding.apply {
            Glide.with(requireContext()).load(food.image).into(imageMeal)
            cardDetailMeal.apply {
                tvTitle.text = food.name
                descOneMeal.text = if (food.halal.equals("True", true)) "halal" else "non-halal"
                buttonMeal.text = String.format(resources.getString(R.string.kcal_template), food.calories)
                resultFat.text = food.fat
                resultProtein.text = food.protein
                resultCarbo.text = food.carbo
                resultReadyIn.text = food.readyMinutes
            }
            val bundle = Bundle().apply {
                putString(Constants.INSTRUCTIONS, food.instructions)
                putStringArrayList(Constants.INGREDIENT, food.ingredients as ArrayList<String>)
            }
            val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailMealFragment, bundle)
            pager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

    }

    private fun back() {
        binding.icBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.ingredients, R.string.instructions
        )
    }

}