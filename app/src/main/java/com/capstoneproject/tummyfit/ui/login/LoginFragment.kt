package com.capstoneproject.tummyfit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginRequestBody
import com.capstoneproject.tummyfit.databinding.FragmentLoginBinding
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signIn()
        toSignUp()
        observeResponse()
    }

    private fun toSignUp() {
        binding.tvSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun signIn() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val pw = etPw.text.toString().trim()
                if (validateInput()) {
                    viewModel.login(LoginRequestBody(username, pw))
                }
            }
        }
    }

    private fun observeResponse() {
        viewModel.login.observe(viewLifecycleOwner) {
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
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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
            if (etPw.text.toString().isEmpty()) {
                flag = false
                tilPw.error = getString(R.string.error_field_empty)
                etPw.requestFocus()
            }
            if (tilUsername.isErrorEnabled) {
                flag = false
            } else if (tilPw.isErrorEnabled) {
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