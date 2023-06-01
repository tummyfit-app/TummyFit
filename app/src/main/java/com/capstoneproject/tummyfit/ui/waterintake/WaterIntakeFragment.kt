package com.capstoneproject.tummyfit.ui.waterintake

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.databinding.FragmentSearchMealsBinding
import com.capstoneproject.tummyfit.databinding.FragmentWaterIntakeBinding
import com.capstoneproject.tummyfit.ui.search.SearchMealsViewModel
import com.capstoneproject.tummyfit.ui.search.adapter.SearchAdapter

class WaterIntakeFragment : Fragment() {

    private var _binding: FragmentWaterIntakeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WaterIntakeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterIntakeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toBack()
    }

    private fun toBack(){
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}