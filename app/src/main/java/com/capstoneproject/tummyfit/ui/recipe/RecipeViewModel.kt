package com.capstoneproject.tummyfit.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.FoodRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {
    private val _popular = MutableLiveData<Resource<GetFoodResponse>>()
    val popular: LiveData<Resource<GetFoodResponse>> get() = _popular

    private val _halal = MutableLiveData<Resource<GetFoodResponse>>()
    val halal: LiveData<Resource<GetFoodResponse>> get() = _halal

    private val _cheap = MutableLiveData<Resource<GetFoodResponse>>()
    val cheap: LiveData<Resource<GetFoodResponse>> get() = _cheap

    private val _fastFood = MutableLiveData<Resource<GetFoodResponse>>()
    val fastFood: LiveData<Resource<GetFoodResponse>> get() = _fastFood

    fun getFoodPopular() = viewModelScope.launch(Dispatchers.IO) {
        _popular.postValue(Resource.Loading())
        val data = foodRepository.recipeFoodsByCategory(
            authRepository.getToken().first(),
            "1",
            null,
            null,
            null
        )
        viewModelScope.launch(Dispatchers.Main) {
            _popular.postValue(data)
        }
    }

    fun getFoodHalal() = viewModelScope.launch(Dispatchers.IO) {
        _halal.postValue(Resource.Loading())
        val data = foodRepository.recipeFoodsByCategory(
            authRepository.getToken().first(),
            null,
            "1",
            null,
            null
        )
        viewModelScope.launch(Dispatchers.Main) {
            _halal.postValue(data)
        }
    }

    fun getFoodCheap() = viewModelScope.launch(Dispatchers.IO) {
        _cheap.postValue(Resource.Loading())
        val data = foodRepository.recipeFoodsByCategory(
            authRepository.getToken().first(),
            null,
            null,
            "10",
            null
        )
        viewModelScope.launch(Dispatchers.Main) {
            _cheap.postValue(data)
        }
    }

    fun getFoodFast() = viewModelScope.launch(Dispatchers.IO) {
        _fastFood.postValue(Resource.Loading())
        val data = foodRepository.recipeFoodsByCategory(
            authRepository.getToken().first(),
            null,
            null,
            null,
            "15"
        )
        viewModelScope.launch(Dispatchers.Main) {
            _fastFood.postValue(data)
        }
    }
}