package com.capstoneproject.tummyfit.ui.mealsrecommend

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.tummyfit.R

class MealsRecommendFragment : Fragment() {

    companion object {
        fun newInstance() = MealsRecommendFragment()
    }

    private lateinit var viewModel: MealsRecommendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals_recommend, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MealsRecommendViewModel::class.java)
        // TODO: Use the ViewModel
    }

}