package com.capstoneproject.tummyfit.di

import com.capstoneproject.tummyfit.data.local.datasource.AuthLocalDataSource
import com.capstoneproject.tummyfit.data.local.datasource.AuthLocalDataSourceImpl
import com.capstoneproject.tummyfit.data.local.datasource.FavoriteLocalDataSource
import com.capstoneproject.tummyfit.data.local.datasource.FavoriteLocalDataSourceImpl
import com.capstoneproject.tummyfit.data.remote.datasource.AuthRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.datasource.AuthRemoteDataSourceImpl
import com.capstoneproject.tummyfit.data.remote.datasource.FoodRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.datasource.FoodRemoteDataSourceImpl
import com.capstoneproject.tummyfit.data.remote.datasource.UserRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.datasource.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    abstract fun provideFoodRemoteDataSource(foodRemoteDataSourceImpl: FoodRemoteDataSourceImpl): FoodRemoteDataSource

    @Binds
    abstract fun provideFavoriteLocalDataSource(favoriteLocalDataSourceImpl: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource
}