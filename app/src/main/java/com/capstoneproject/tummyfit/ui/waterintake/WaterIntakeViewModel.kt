package com.capstoneproject.tummyfit.ui.waterintake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.WaterIntakeRepository
import com.capstoneproject.tummyfit.utils.getCurrentDate
import com.capstoneproject.tummyfit.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterIntakeViewModel @Inject constructor(
    private val waterIntakeRepository: WaterIntakeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _water = MutableLiveData<WaterIntakeEntity>()
    val water: LiveData<WaterIntakeEntity> get() = _water

    private val _listWater = MutableLiveData<Resource<List<WaterIntakeEntity>>>()
    val listWater: LiveData<Resource<List<WaterIntakeEntity>>> get() = _listWater

    init {
        insertWaterIntake()
    }

    private fun insertWaterIntake() = viewModelScope.launch {
        waterIntakeRepository.insertWaterIntake(
            WaterIntakeEntity(
                id_user = authRepository.getId().first(),
                date = getCurrentDate(),
                now_intake = 0,
                total_intake = 3000
            )
        )
        getListWaterIntake()
    }

    fun updateIntake(now_intake: Int) = viewModelScope.launch {
        waterIntakeRepository.updateIntake(
            now_intake, authRepository.getId().first(), getCurrentDate()
        )
        getStatsWaterIntake()
    }

    fun getStatsWaterIntake() = viewModelScope.launch {
        delay(500L)
        _water.postValue(
            waterIntakeRepository.getStatsWaterIntake(
                authRepository.getId().first(), getCurrentDate()
            )
        )
    }

    fun getListWaterIntake() = viewModelScope.launch(Dispatchers.IO) {
        _listWater.postValue(Resource.Loading())
        val data = waterIntakeRepository.getListWaterIntake(authRepository.getId().first())
        viewModelScope.launch(Dispatchers.Main) {
            _listWater.postValue(data)
        }
    }

}