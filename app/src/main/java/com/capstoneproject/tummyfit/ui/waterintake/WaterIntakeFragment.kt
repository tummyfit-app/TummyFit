package com.capstoneproject.tummyfit.ui.waterintake

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.tummyfit.R

class WaterIntakeFragment : Fragment() {

    companion object {
        fun newInstance() = WaterIntakeFragment()
    }

    private lateinit var viewModel: WaterIntakeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_water_intake, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WaterIntakeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}