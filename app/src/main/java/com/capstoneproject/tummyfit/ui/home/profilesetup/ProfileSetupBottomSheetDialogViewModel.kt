package com.capstoneproject.tummyfit.ui.home.profilesetup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSetupBottomSheetDialogViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _response = MutableLiveData<Resource<PostUserDescResponse>>()
    val response: LiveData<Resource<PostUserDescResponse>> get() = _response

    fun postUser(postUserDescRequestBody: PostUserDescRequestBody) {
        _response.postValue(Resource.Loading())
        viewModelScope.launch {
            _response.postValue(
                userRepository.postUserDesc(
                    authRepository.getToken().first(),
                    postUserDescRequestBody
                )
            )
        }
    }

}