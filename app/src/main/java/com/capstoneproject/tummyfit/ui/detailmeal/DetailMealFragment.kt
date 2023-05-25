package com.capstoneproject.tummyfit.ui.detailmeal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.tummyfit.R

class DetailMealFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMealFragment()
    }

    private lateinit var viewModel: DetailMealViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_meal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailMealViewModel::class.java)
        // TODO: Use the ViewModel
    }

}