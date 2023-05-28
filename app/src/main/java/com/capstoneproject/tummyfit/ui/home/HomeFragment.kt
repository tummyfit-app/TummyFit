package com.capstoneproject.tummyfit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.user.UserDescription
import com.capstoneproject.tummyfit.databinding.FragmentHomeBinding
import com.capstoneproject.tummyfit.ui.home.adapter.TryItAdapter
import com.capstoneproject.tummyfit.utils.scoreIbm
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var tryItAdapter: TryItAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        viewModel.getFoodsTryIt()
        initTryItList()
        observeData()
        binding.listTodayMeals.shimmerTodayMeals.startShimmer()
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    it.data?.data?.userDescription?.let { it1 -> bindToView(it1) }
                }

                is Resource.Error -> {
                    showLoading(true)
                    findNavController().navigate(R.id.action_homeFragment_to_profileSetupBottomSheetDialogFragment)
                }
            }
        }
        viewModel.foodTryIt.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.listTryIt.shimmerTryIt.isVisible = true
                    binding.listTryIt.rvTryIt.isVisible = false
                }

                is Resource.Empty -> {
                    binding.listTryIt.shimmerTryIt.isVisible = true
                    binding.listTryIt.rvTryIt.isVisible = false
                }

                is Resource.Success -> {
                    binding.listTryIt.shimmerTryIt.isVisible = false
                    binding.listTryIt.rvTryIt.isVisible = true
                    tryItAdapter.differ.submitList(it.data?.data?.foods)
                }

                is Resource.Error -> {
                    binding.listTryIt.shimmerTryIt.isVisible = true
                    binding.listTryIt.rvTryIt.isVisible = false
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.apply {
            shimmerHomeHeader.isVisible = boolean
            shimmerCardData.isVisible = boolean
            cardData.cardData.isVisible = !boolean
            homeHeader.homeHeader.isVisible = !boolean
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser()
        viewModel.getFoodsTryIt()
    }

    private fun bindToView(userDescription: UserDescription) {
        binding.homeHeader.apply {
            username.text = "${userDescription.user.firstname} ${userDescription.user.lastname}"
            email.text = userDescription.user.email
        }
        binding.cardData.apply {
            resultHeight.text =
                String.format(resources.getString(R.string.cm_template), userDescription.height)
            resultWeight.text =
                String.format(resources.getString(R.string.kg_template), userDescription.weight)
            resultIbm.text = scoreIbm(
                userDescription.weight,
                userDescription.height
            ).toString()
        }
    }

    private fun initTryItList() {
        tryItAdapter = TryItAdapter()
        binding.listTryIt.rvTryIt.apply {
            adapter = tryItAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}