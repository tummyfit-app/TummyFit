package com.capstoneproject.tummyfit.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.databinding.FragmentSplashScreenBinding
import com.capstoneproject.tummyfit.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)
        loadScreen()
        return binding.root
    }

    private fun loadScreen() {
        Handler().postDelayed({
            lifecycleScope.launchWhenCreated {
                checkUserSession()
            }
        }, Constants.LOADING_TIME)
    }

    private fun checkUserSession() {
        viewModel.session.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewModel.getOnBoarding.observe(viewLifecycleOwner){isFinish ->
                    if (isFinish == true){
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                    }else {
                        findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
                    }
                }
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}