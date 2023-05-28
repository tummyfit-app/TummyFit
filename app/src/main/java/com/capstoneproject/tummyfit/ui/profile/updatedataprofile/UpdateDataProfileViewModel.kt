package com.capstoneproject.tummyfit.ui.profile.updatedataprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateDataProfileViewModel @Inject constructor(
    private val userRepository: UserRepository, private val authRepository: AuthRepository
) : ViewModel() {
    private val _response = MutableLiveData<Resource<UpdateUserDescResponse>>()
    val response: LiveData<Resource<UpdateUserDescResponse>> get() = _response

    private val _user = MutableLiveData<Resource<GetUserResponse>>()
    val user: LiveData<Resource<GetUserResponse>> get() = _user

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(Resource.Loading())
        delay(1000L)
        viewModelScope.launch(Dispatchers.Main) {
            _user.postValue(userRepository.getUserDesc(authRepository.getToken().first()))
        }
    }

    fun updateUserDesc(id: String, updateUserDescRequestBody: UpdateUserDescRequestBody) =
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(Resource.Loading())
            delay(1000L)
            viewModelScope.launch(Dispatchers.Main) {
                _response.postValue(
                    userRepository.updateUserDesc(
                        authRepository.getToken().first(),
                        id,
                        updateUserDescRequestBody
                    )
                )
            }
        }
}