package com.capstoneproject.tummyfit.ui.profile.editprofile

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
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.UserDescription
import com.capstoneproject.tummyfit.databinding.FragmentEditProfileBinding
import com.capstoneproject.tummyfit.databinding.FragmentProfileBinding
import com.capstoneproject.tummyfit.ui.profile.ProfileViewModel
import com.capstoneproject.tummyfit.utils.scoreIbm
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.wrapper.Resource
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        back()
        observeData()
        editProfile()
    }

    private fun editProfile() {
        binding.apply {
            btnEditProfile.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val pw = etPw.text.toString().trim()
                val firstName = etFirstname.text.toString().trim()
                val lastName = etLastname.text.toString().trim()
                if (validateInput()) {
                    viewModel.updateUser(
                        UpdateUserRequestBody(username, firstName, lastName, email, pw)
                    )
                }
            }
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
                        it.data?.response?.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
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
                    it.payload?.data?.userDescription?.let { it1 -> bindToView(it1) }
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
            etFirstname.setText(userDescription.user.firstname)
            etLastname.setText(userDescription.user.lastname)
            etUsername.setText(userDescription.user.username)
            etEmail.setText(userDescription.user.email)
        }
    }


    private fun validateInput(): Boolean {
        var flag = true
        binding.apply {
            if (etUsername.text.toString().isEmpty()) {
                flag = false
                tilUsername.error = getString(R.string.error_field_empty)
                etUsername.requestFocus()
            }
            if (etEmail.text.toString().isEmpty()) {
                flag = false
                tilEmail.error = getString(R.string.error_field_empty)
                etEmail.requestFocus()
            }
            if (etPw.text.toString().isEmpty()) {
                flag = false
                tilPw.error = getString(R.string.error_field_empty)
                etPw.requestFocus()
            }
            if (etFirstname.text.toString().isEmpty()) {
                flag = false
                tilFirstname.error = getString(R.string.error_field_empty)
                etFirstname.requestFocus()
            }
            if (tilUsername.isErrorEnabled) {
                flag = false
            } else if (tilPw.isErrorEnabled) {
                flag = false
            } else if (tilFirstname.isErrorEnabled) {
                flag = false
            } else if (tilEmail.isErrorEnabled) {
                flag = false
            }
        }
        return flag
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