package com.capstoneproject.tummyfit.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstoneproject.tummyfit.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

class AuthDataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    val getToken: Flow<String> = dataStore.data.map {
        it[stringPreferencesKey(Constants.TOKEN_USER_KEY)] ?: ""
    }

    suspend fun setToken(token: String) {
        dataStore.edit {
            it[stringPreferencesKey(Constants.TOKEN_USER_KEY)] = "Bearer $token"
        }
    }

    val getId: Flow<String> = dataStore.data.map {
        it[stringPreferencesKey(Constants.ID_USER_KEY)] ?: ""
    }

    suspend fun setId(id: String) {
        dataStore.edit {
            it[stringPreferencesKey(Constants.ID_USER_KEY)] = id
        }
    }

    suspend fun clearToken() {
        dataStore.edit {
            it.remove(stringPreferencesKey(Constants.TOKEN_USER_KEY))
        }
    }

    suspend fun clearId() {
        dataStore.edit {
            it.remove(stringPreferencesKey(Constants.ID_USER_KEY))
        }
    }
}