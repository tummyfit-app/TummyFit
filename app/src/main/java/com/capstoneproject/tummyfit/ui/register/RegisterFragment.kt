package com.capstoneproject.tummyfit.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.databinding.FragmentOnBoardingBinding
import com.capstoneproject.tummyfit.databinding.FragmentRegisterBinding
import com.capstoneproject.tummyfit.ui.onboarding.OnBoardingViewModel
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUp()
        toSignIn()
        observeResponse()
    }

    private fun toSignIn() {
        binding.tvSignIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun signUp() {
        binding.apply {
            btnSignup.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val pw = etPw.text.toString().trim()
                val name =
                    "${etFirstname.text.toString().trim()} ${etLastname.text.toString().trim()}"
                if (validateInput()) {
                    viewModel.register(
                        RegisterRequestBody(
                            username = username,
                            namauser = name,
                            email = email,
                            password = pw
                        )
                    )
                }
            }
        }
    }

    private fun observeResponse() {
        viewModel.register.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.loadState.isVisible = true
                }
                is Resource.Error -> {
                    binding.loadState.isVisible = false
                    SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                        .setContentText(it.message)
                        .setConfirmText("Try Again")
                        .show()
                }
                is Resource.Empty -> {}
                is Resource.Success -> {
                    binding.loadState.isVisible = false
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
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
            if (etLastname.text.toString().isEmpty()) {
                flag = false
                tilLastname.error = getString(R.string.error_field_empty)
                etLastname.requestFocus()
            }
            if (tilUsername.isErrorEnabled) {
                flag = false
            } else if (tilPw.isErrorEnabled) {
                flag = false
            } else if (tilFirstname.isErrorEnabled) {
                flag = false
            } else if (tilLastname.isErrorEnabled) {
                flag = false
            } else if (tilEmail.isErrorEnabled) {
                flag = false
            }
        }
        return flag
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}