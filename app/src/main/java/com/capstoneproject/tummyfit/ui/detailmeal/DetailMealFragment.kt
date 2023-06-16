package com.capstoneproject.tummyfit.ui.detailmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.food.Food
import com.capstoneproject.tummyfit.databinding.FragmentDetailMealBinding
import com.capstoneproject.tummyfit.ui.detailmeal.adapter.SectionsPagerAdapter
import com.capstoneproject.tummyfit.utils.Constants
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailMealFragment : Fragment() {

    private var _binding: FragmentDetailMealBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailMealViewModel by viewModels()
    private val navArgs: DetailMealFragmentArgs by navArgs()
    private var isFavorite by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailFood(navArgs.id.toString())
        viewModel.isFavorite(navArgs.id.toString())
        observeData()
        back()
    }

    private fun observeData() {
        viewModel.detail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}

                is Resource.Error -> {
                    showSnackbar(requireView(), it.message.toString())
                }

                is Resource.Success -> {
                    it.data?.data?.food?.let { it1 ->
                        bindToView(it1)
                        selectFavorite(
                            it1
                        )
                    }
                }

                is Resource.Empty -> {}
            }
        }
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            binding.cardDetailMeal.icFav.apply {
                if (it == true) {
                    setImageResource(R.drawable.baseline_star_24)
                } else {
                    setImageResource(R.drawable.baseline_star_outline_24)
                }
                isFavorite = it
            }

        }
    }

    private fun selectFavorite(food: Food) {
        binding.cardDetailMeal.apply {
            icFav.setOnClickListener {
                if (!isFavorite) {
                    viewModel.addFavorite(
                        food.id,
                        food.name,
                        food.image,
                        food.calories,
                        "${food.dishType} | ${
                            if (food.halal.equals(
                                    "True",
                                    true
                                )
                            ) "halal" else "non-halal"
                        }",
                        true
                    )
                    icFav.setImageResource(R.drawable.baseline_star_24)
                    isFavorite = true
                    showSnackbar(requireView(), "Success add to Favorite")
                } else {
                    viewModel.removeFavorite(
                        food.id,
                        food.name,
                        food.image,
                        food.calories,
                        "${food.dishType} | ${
                            if (food.halal.equals(
                                    "True",
                                    true
                                )
                            ) "halal" else "non-halal"
                        }",
                        true
                    )
                    icFav.setImageResource(R.drawable.baseline_star_outline_24)
                    isFavorite = false
                    showSnackbar(requireView(), "Success delete from Favorite")
                }
            }
        }
    }

    private fun bindToView(food: Food) {
        binding.apply {
            Glide.with(requireContext()).load(food.image).error(R.drawable.load_img_error).centerCrop().into(imageMeal)
            cardDetailMeal.apply {
                tvTitle.text = food.name
                descOneMeal.text = if (food.halal.equals("True", true)) "halal" else "non-halal"
                buttonMeal.text =
                    String.format(resources.getString(R.string.kcal_template), food.calories)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.ingredients, R.string.instructions
        )
    }

}