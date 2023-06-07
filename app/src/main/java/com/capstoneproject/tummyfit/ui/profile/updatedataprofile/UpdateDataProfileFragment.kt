package com.capstoneproject.tummyfit.ui.profile.updatedataprofile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.UserDescription
import com.capstoneproject.tummyfit.databinding.FragmentEditProfileBinding
import com.capstoneproject.tummyfit.databinding.FragmentUpdateDataProfileBinding
import com.capstoneproject.tummyfit.ui.profile.ProfileViewModel
import com.capstoneproject.tummyfit.utils.showDatePicker
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateDataProfileFragment : Fragment() {

    private var _binding: FragmentUpdateDataProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateDataProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateDataProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        observeData()
        back()
        binding.etBirthday.setOnClickListener {
            showDatePicker(parentFragmentManager, binding.etBirthday)
        }
    }

    private fun observeData() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    FancyToast.makeText(
                        requireContext(),
                        it.data?.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    findNavController().navigate(R.id.action_updateDataProfileFragment_to_profileFragment)
                }

                is Resource.Error -> {
                    showLoading(false)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
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
                    it.data?.data?.userDescription?.let { it1 ->
                        bindToView(it1)
                        updateDataProfile(it1)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun bindToView(userDescription: UserDescription) {
        binding.apply {
            etBirthday.setText(userDescription.birthDate)
            etHeight.setText(userDescription.height.toString())
            etWeight.setText(userDescription.weight.toString())
            tilPurpose.isHelperTextEnabled = true
            tilPurpose.helperText = "Your last purpose is ${userDescription.purpose}"
            tilDailyActivity.isHelperTextEnabled = true
            tilDailyActivity.helperText =
                "Your last daily activity is ${userDescription.dailyActivity}"
            chipGluttenFree.isChecked = userDescription.glutenFree.equals("yes", true)
            chipHalal.isChecked = userDescription.halal.equals("yes", true)
            chipDairyFree.isChecked = userDescription.dairyFree.equals("yes", true)
            chipVegan.isChecked = userDescription.vegan.equals("yes", true)
            chipVegetarian.isChecked = userDescription.vegetarian.equals("yes", true)
        }
    }

    private fun updateDataProfile(userDescription: UserDescription) {
        binding.apply {
            btnUpdateDataProfile.setOnClickListener {
                val birthday = etBirthday.text.toString().trim()
                val height = etHeight.text.toString().trim().toInt()
                val weight = etWeight.text.toString().trim().toInt()
                val purpose = etPurpose.text.toString().trim().ifEmpty { userDescription.purpose }
                val dailyActivity =
                    etDailyActivity.text.toString().trim().ifEmpty { userDescription.dailyActivity }
                val dairy = if (chipDairyFree.isChecked) "yes" else "no"
                val halal = if (chipHalal.isChecked) "yes" else "no"
                val vegetarian = if (chipVegetarian.isChecked) "yes" else "no"
                val vegan = if (chipVegan.isChecked) "yes" else "no"
                val glutten = if (chipGluttenFree.isChecked) "yes" else "no"
                viewModel.updateUserDesc(
                    userDescription.id,
                    UpdateUserDescRequestBody(
                        halal,
                        purpose,
                        vegetarian,
                        weight,
                        vegan,
                        birthday,
                        glutten,
                        dairy,
                        dailyActivity,
                        height
                    )
                )
            }
        }
    }

    private fun showLoading(boolean: Boolean) {
        binding.bgView.isVisible = boolean
        binding.loadState.isVisible = boolean
    }

    private fun back() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}