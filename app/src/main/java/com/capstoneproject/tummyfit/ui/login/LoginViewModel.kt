package com.capstoneproject.tummyfit.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login

    fun login(loginRequestBody: LoginRequestBody) {
        _login.postValue(Resource.Loading())
        viewModelScope.launch {
            _login.postValue(authRepository.login(loginRequestBody))
        }
    }
}