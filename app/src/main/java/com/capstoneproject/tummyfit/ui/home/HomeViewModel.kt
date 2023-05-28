package com.capstoneproject.tummyfit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.FoodRepository
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _user = MutableLiveData<Resource<GetUserResponse>>()
    val user: LiveData<Resource<GetUserResponse>> get() = _user

    private val _foodTryIt = MutableLiveData<Resource<GetFoodResponse>>()
    val foodTryIt: LiveData<Resource<GetFoodResponse>> get() = _foodTryIt

    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        _user.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _user.postValue(userRepository.getUserDesc(authRepository.getToken().first()))
        }
    }

    fun getFoodsTryIt() = viewModelScope.launch(Dispatchers.IO) {
        _foodTryIt.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _foodTryIt.postValue(foodRepository.getListFoods(authRepository.getToken().first()))
        }
    }
}