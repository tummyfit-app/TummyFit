package com.capstoneproject.tummyfit.ui.detailmeal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstoneproject.tummyfit.databinding.FragmentInstructionsBinding
import com.capstoneproject.tummyfit.utils.Constants

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("INFO INS", arguments?.getString(Constants.INSTRUCTIONS).toString())
        binding.tvInstructions.text =
            arguments?.getString(Constants.INSTRUCTIONS)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}