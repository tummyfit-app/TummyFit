package com.capstoneproject.tummyfit.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _register = MutableLiveData<Resource<RegisterResponse>>()
    val register: LiveData<Resource<RegisterResponse>> get() = _register

    fun register(registerRequestBody: RegisterRequestBody) {
        _register.postValue(Resource.Loading())
        viewModelScope.launch {
            _register.postValue(authRepository.register(registerRequestBody))
        }
    }
}