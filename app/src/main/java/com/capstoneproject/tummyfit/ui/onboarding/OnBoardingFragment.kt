package com.capstoneproject.tummyfit.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.databinding.FragmentOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnJoinNow.setOnClickListener {
            viewModel.saveOnBoarding()
            it.findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
        binding.tvSkip.setOnClickListener {
            viewModel.saveOnBoarding()
            it.findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}