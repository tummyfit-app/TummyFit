package com.capstoneproject.tummyfit.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    fun saveOnBoarding() = viewModelScope.launch {
        authRepository.saveOnBoarding(true)
    }
}