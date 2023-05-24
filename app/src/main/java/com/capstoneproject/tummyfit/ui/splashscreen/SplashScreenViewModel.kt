package com.capstoneproject.tummyfit.ui.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstoneproject.tummyfit.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    val session = authRepository.getToken().asLiveData()
}