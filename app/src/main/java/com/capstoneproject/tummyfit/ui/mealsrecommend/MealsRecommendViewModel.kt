package com.capstoneproject.tummyfit.ui.mealsrecommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodPredictResponse
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.FoodRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsRecommendViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _foodPredict = MutableLiveData<Resource<GetFoodPredictResponse>>()
    val foodPredict: LiveData<Resource<GetFoodPredictResponse>> get() = _foodPredict

    fun getFoodPredict() = viewModelScope.launch(Dispatchers.IO) {
        _foodPredict.postValue(Resource.Loading())
        val data = foodRepository.getFoodPredict(authRepository.getToken().first())
        viewModelScope.launch(Dispatchers.Main) {
            _foodPredict.postValue(data)
        }
    }
}