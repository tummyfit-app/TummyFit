package com.capstoneproject.tummyfit.ui.detailmeal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
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
class DetailMealViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _detail = MutableLiveData<Resource<GetFoodDetailResponse>>()
    val detail: LiveData<Resource<GetFoodDetailResponse>> get() = _detail

    fun getDetailFood(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _detail.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _detail.postValue(foodRepository.getDetailFood(authRepository.getToken().first(), id))
        }
    }
}