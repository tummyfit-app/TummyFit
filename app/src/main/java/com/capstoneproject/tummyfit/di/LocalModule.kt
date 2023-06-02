package com.capstoneproject.tummyfit.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.capstoneproject.tummyfit.data.local.database.AppDatabase
import com.capstoneproject.tummyfit.data.local.database.dao.FavoriteMealDao
import com.capstoneproject.tummyfit.data.local.database.dao.WaterIntakeDao
import com.capstoneproject.tummyfit.data.local.preference.AuthDataStoreManager
import com.capstoneproject.tummyfit.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

private val Context.dataStorePreferences by preferencesDataStore(
    name = Constants.DATASTORE_PREFERENCES
)

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoriteMealDao(appDatabase: AppDatabase): FavoriteMealDao =
        appDatabase.favoriteMealDao()

    @Provides
    fun provideWaterIntakeDao(appDatabase: AppDatabase): WaterIntakeDao =
        appDatabase.waterIntakeDao()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStorePreferences

    @Singleton
    @Provides
    fun provideAuthDataStoreManager(dataStore: DataStore<Preferences>): AuthDataStoreManager =
        AuthDataStoreManager(dataStore)
}