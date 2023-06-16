package com.capstoneproject.tummyfit.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.capstoneproject.tummyfit.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class OnBoardingDataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveOnBoarding(isFinished: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(Constants.ON_BOARDING_KEY)] = isFinished
        }
    }

    fun getOnBoarding(): Flow<Boolean?> = dataStore.data.map {
        it[booleanPreferencesKey(Constants.ON_BOARDING_KEY)]
    }
}