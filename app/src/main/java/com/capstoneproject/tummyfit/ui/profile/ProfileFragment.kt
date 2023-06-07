package com.capstoneproject.tummyfit.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.user.UserDescription
import com.capstoneproject.tummyfit.databinding.FragmentProfileBinding
import com.capstoneproject.tummyfit.utils.scoreIbm
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        viewModel.getFoodPredict()
        observeData()
        logout()
        toEditProfile()
        toUpdateDataProfile()
    }

    private fun toEditProfile() {
        binding.btnEditProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    private fun toUpdateDataProfile() {
        binding.btnUpdateDataProfile.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_profileFragment_to_updateDataProfileFragment)
        }
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
                    it.payload?.data?.userDescription?.let { user -> bindToView(user) }
                }

                is Resource.Error -> {
                    showLoading(true)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.foodPredict.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    binding.cardData.totalKcal.text = it.data?.prediction?.get(7)?.requirement.toString()
                }

                is Resource.Error -> {
                    showLoading(true)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.apply {
            profileShimmer.isVisible = boolean
            cardData.cardData.isVisible = !boolean
            tvName.isVisible = !boolean
            tvUsername.isVisible = !boolean
            pictUser.isVisible = !boolean
        }
    }

    private fun bindToView(userDescription: UserDescription) {
        with(binding){
            Glide.with(pictUser).load(userDescription.user.urlprofile).into(pictUser)
            tvName.text = userDescription.user.firstname + " " + userDescription.user.lastname
            tvUsername.text = userDescription.user.username
            cardData.apply {
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
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.clearSession()
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}