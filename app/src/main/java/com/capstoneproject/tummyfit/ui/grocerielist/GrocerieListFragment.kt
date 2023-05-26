package com.capstoneproject.tummyfit.ui.grocerielist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstoneproject.tummyfit.R

class GrocerieListFragment : Fragment() {

    companion object {
        fun newInstance() = GrocerieListFragment()
    }

    private lateinit var viewModel: GrocerieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grocerie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GrocerieListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}