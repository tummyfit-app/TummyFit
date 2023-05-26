package com.capstoneproject.tummyfit.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.databinding.FragmentProfileBinding
import com.capstoneproject.tummyfit.databinding.FragmentSearchMealsBinding
import com.capstoneproject.tummyfit.ui.profile.ProfileViewModel

class SearchMealsFragment : Fragment() {

    private var _binding: FragmentSearchMealsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchMealsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMealsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listSearch.shimmerSearch.startShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}