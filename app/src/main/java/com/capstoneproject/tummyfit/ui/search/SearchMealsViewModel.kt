package com.capstoneproject.tummyfit.ui.search

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
class SearchMealsViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _search = MutableLiveData<Resource<GetFoodResponse>>()
    val search: LiveData<Resource<GetFoodResponse>> get() = _search

    init {
        getPerfectMatch()
    }

    private fun getPerfectMatch() = viewModelScope.launch(Dispatchers.IO) {
        _search.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.Main) {
            _search.postValue(foodRepository.getListFoods(authRepository.getToken().first()))
        }
    }

    fun searchFoodsByCategory(name: String? = null, category: String? = null) = viewModelScope.launch(Dispatchers.IO) {
        _search.postValue(Resource.Loading())
        val data = foodRepository.searchFoodsByCategory(authRepository.getToken().first(), name, category)
        viewModelScope.launch(Dispatchers.Main) {
            data.payload?.data?.foods?.let {
                if (it.isNotEmpty()) {
                    _search.postValue(data)
                } else {
                    _search.postValue(Resource.Empty())
                }
            }
        }
    }
}