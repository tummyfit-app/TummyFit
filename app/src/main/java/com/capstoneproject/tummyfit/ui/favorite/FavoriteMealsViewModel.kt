package com.capstoneproject.tummyfit.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.FavoriteRepository
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMealsViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _favorite = MutableLiveData<Resource<List<FavoriteMealEntity>>>()
    val favorite: LiveData<Resource<List<FavoriteMealEntity>>> = _favorite

    fun getAllFavorites() = viewModelScope.launch {
        _favorite.postValue(Resource.Loading())
        if (favoriteRepository.getAllFavorites(authRepository.getId().first()).payload?.size!! > 0) {
            _favorite.postValue(favoriteRepository.getAllFavorites(authRepository.getId().first()))
        } else {
            _favorite.postValue(Resource.Empty())
        }
    }
}