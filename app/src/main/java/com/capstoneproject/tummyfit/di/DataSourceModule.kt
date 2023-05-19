package com.capstoneproject.tummyfit.di

import com.capstoneproject.tummyfit.data.local.datasource.AuthLocalDataSource
import com.capstoneproject.tummyfit.data.local.datasource.AuthLocalDataSourceImpl
import com.capstoneproject.tummyfit.data.remote.datasource.AuthRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.datasource.AuthRemoteDataSourceImpl
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
}