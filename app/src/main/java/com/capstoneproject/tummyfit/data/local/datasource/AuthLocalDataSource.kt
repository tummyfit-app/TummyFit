package com.capstoneproject.tummyfit.data.local.datasource

import com.capstoneproject.tummyfit.data.local.preference.AuthDataStoreManager
import com.capstoneproject.tummyfit.data.local.preference.OnBoardingDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface AuthLocalDataSource {
    fun getToken(): Flow<String>
    suspend fun setToken(token: String)
    suspend fun clearToken()
    fun getId(): Flow<String>
    suspend fun setId(id: String)
    suspend fun clearId()
    suspend fun saveOnBoarding(isFinished: Boolean)
    fun getOnBoarding(): Flow<Boolean?>
}

class AuthLocalDataSourceImpl @Inject constructor(
    private val authDataStoreManager: AuthDataStoreManager,
    private val onBoardingDataStoreManager: OnBoardingDataStoreManager
) :
    AuthLocalDataSource {
    override fun getToken(): Flow<String> = authDataStoreManager.getToken
    override suspend fun setToken(token: String) = authDataStoreManager.setToken(token)
    override suspend fun clearToken() = authDataStoreManager.clearToken()
    override fun getId(): Flow<String> = authDataStoreManager.getId
    override suspend fun setId(id: String) = authDataStoreManager.setId(id)
    override suspend fun clearId() = authDataStoreManager.clearId()
    override suspend fun saveOnBoarding(isFinished: Boolean) = onBoardingDataStoreManager.saveOnBoarding(isFinished)
    override fun getOnBoarding(): Flow<Boolean?> = onBoardingDataStoreManager.getOnBoarding()
}