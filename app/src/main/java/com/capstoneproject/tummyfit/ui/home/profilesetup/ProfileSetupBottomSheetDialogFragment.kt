package com.capstoneproject.tummyfit.ui.home.profilesetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.databinding.FragmentProfileSetupBottomSheetBinding
import com.capstoneproject.tummyfit.utils.showDatePicker
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSetupBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentProfileSetupBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileSetupBottomSheetDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSetupBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        binding.profileSetupContent.etBirthday.setOnClickListener {
            showDatePicker(parentFragmentManager, binding.profileSetupContent.etBirthday)
        }
        submit()
    }

    private fun submit() {
        binding.profileSetupContent.apply {
            btnSubmit.setOnClickListener {
                if (isValid()) {
                    val weight = etWeight.text.toString().trim().toInt()
                    val height = etHeight.text.toString().trim().toInt()
                    val gender = getSelectedGender()
                    val birthday = etBirthday.text.toString().trim()
                    val purpose = etPurpose.text.toString().trim()
                    val daily = etDailyActivity.text.toString().trim()
                    val dairy = if (chipDairyFree.isChecked) "yes" else "no"
                    val halal = if (chipHalal.isChecked) "yes" else "no"
                    val vegetarian = if (chipVegetarian.isChecked) "yes" else "no"
                    val vegan = if (chipVegan.isChecked) "yes" else "no"
                    val glutten = if (chipGluttenFree.isChecked) "yes" else "no"
                    viewModel.postUser(
                        PostUserDescRequestBody(
                            halal = halal,
                            purpose = purpose,
                            sex = gender,
                            vegetarian = vegetarian,
                            weight = weight,
                            vegan = vegan,
                            birthDate = birthday,
                            glutenFree = glutten,
                            dairyFree = dairy,
                            dailyActivity = daily,
                            height = height
                        )
                    )
                    observeData()
                }
            }
        }
    }

    private fun observeData() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {}
                is Resource.Success -> {
                    FancyToast.makeText(
                        requireContext(),
                        it.data?.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                   findNavController().navigate(R.id.action_profileSetupBottomSheetDialogFragment_to_homeFragment)
                }
                is Resource.Empty -> {}
            }
        }
    }

    private fun getSelectedGender(): String {
        var gender = "male"
        when (binding.profileSetupContent.rgGender.checkedRadioButtonId) {
            R.id.rb_male -> gender = "male"
            R.id.rb_female -> gender = "female"
        }
        return gender
    }

    private fun isValid(): Boolean {
        var flag = true
        binding.profileSetupContent.apply {
            if (etWeight.text.toString().isEmpty()) {
                flag = false
                showSnackbar(requireView(), "Weight cannot be empty!")
                etWeight.requestFocus()
            } else if (etHeight.text.toString().isEmpty()) {
                flag = false
                showSnackbar(requireView(), "Height cannot be empty!")
                etHeight.requestFocus()
            } else if (etBirthday.text.toString().isEmpty()) {
                flag = false
                showSnackbar(requireView(), "Birthday date cannot be empty!")
                etBirthday.requestFocus()
            } else if (etPurpose.text.toString().isEmpty()) {
                flag = false
                showSnackbar(requireView(), "Purpose cannot be empty!")
                etPurpose.requestFocus()
            } else if (etDailyActivity.text.toString().isEmpty()) {
                flag = false
                showSnackbar(requireView(), "Daily activity cannot be empty!")
                etDailyActivity.requestFocus()
            }
        }
        return flag
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}