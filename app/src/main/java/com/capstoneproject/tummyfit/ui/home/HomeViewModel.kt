package com.capstoneproject.tummyfit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import com.capstoneproject.tummyfit.data.remote.model.user.UserResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<Resource<UserResponse>>()
    val user: LiveData<Resource<UserResponse>> get() = _user

    fun getUser() {
        _user.postValue(Resource.Loading())
        viewModelScope.launch {
            _user.postValue(userRepository.getUserDesc(authRepository.getToken().first()))
        }
    }
}