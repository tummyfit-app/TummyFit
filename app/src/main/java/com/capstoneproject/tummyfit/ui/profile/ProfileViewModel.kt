package com.capstoneproject.tummyfit.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodPredictResponse
import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.FoodRepository
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {
    private val _user = MutableLiveData<Resource<GetUserResponse>>()
    val user: LiveData<Resource<GetUserResponse>> get() = _user

    private val _foodPredict = MutableLiveData<Resource<GetFoodPredictResponse>>()
    val foodPredict: LiveData<Resource<GetFoodPredictResponse>> get() = _foodPredict

    fun getFoodPredict() = viewModelScope.launch(Dispatchers.IO) {
        _foodPredict.postValue(Resource.Loading())
        val data = foodRepository.getFoodPredict(authRepository.getToken().first())
        viewModelScope.launch(Dispatchers.Main) {
            _foodPredict.postValue(data)
        }
    }

    fun clearSession() = viewModelScope.launch {
        authRepository.clear()
        authRepository.clearId()
    }

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(Resource.Loading())
        delay(1000L)
        viewModelScope.launch(Dispatchers.Main) {
            _user.postValue(userRepository.getUserDesc(authRepository.getToken().first()))
        }
    }
}