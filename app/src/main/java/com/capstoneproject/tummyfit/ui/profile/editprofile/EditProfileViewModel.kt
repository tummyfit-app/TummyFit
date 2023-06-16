package com.capstoneproject.tummyfit.ui.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val userRepository: UserRepository
) : ViewModel() {
    private val _response = MutableLiveData<Resource<UpdateUserResponse>>()
    val response: LiveData<Resource<UpdateUserResponse>> get() = _response

    private val _updatePhoto = MutableLiveData<Resource<UpdateUserResponse>>()
    val updatePhoto: LiveData<Resource<UpdateUserResponse>> get() = _updatePhoto

    private val _user = MutableLiveData<Resource<GetUserResponse>>()
    val user: LiveData<Resource<GetUserResponse>> get() = _user

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(Resource.Loading())
        delay(1000L)
        viewModelScope.launch(Dispatchers.Main) {
            _user.postValue(userRepository.getUserDesc(authRepository.getToken().first()))
        }
    }

    fun updateUser(updateUserRequestBody: UpdateUserRequestBody) =
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(Resource.Loading())
            delay(1000L)
            viewModelScope.launch(Dispatchers.Main) {
                _response.postValue(
                    authRepository.updateUser(
                        authRepository.getToken().first(), updateUserRequestBody
                    )
                )
            }
        }

    fun updatePhotoUser(file: MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO) {
        _updatePhoto.postValue(Resource.Loading())
        val response = authRepository.updatePhotoUser(authRepository.getToken().first(), file)
        viewModelScope.launch(Dispatchers.Main) {
            _updatePhoto.postValue(response)
        }
    }
}