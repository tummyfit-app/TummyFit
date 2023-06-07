package com.capstoneproject.tummyfit.ui.detailmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.tummyfit.databinding.FragmentIngredientBinding
import com.capstoneproject.tummyfit.ui.detailmeal.adapter.IngredientAdapter
import com.capstoneproject.tummyfit.utils.Constants


class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientAdapter = IngredientAdapter()
        ingredientAdapter.differ.submitList(arguments?.getStringArrayList(Constants.INGREDIENT))
        binding.rvIngredients.apply {
            adapter = ingredientAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}