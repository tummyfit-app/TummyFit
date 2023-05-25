package com.capstoneproject.tummyfit.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.tummyfit.R

class SearchMealsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchMealsFragment()
    }

    private lateinit var viewModel: SearchMealsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_meals, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchMealsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}