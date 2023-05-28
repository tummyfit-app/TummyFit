package com.capstoneproject.tummyfit.di

import com.capstoneproject.tummyfit.data.repository.AuthRepository
import com.capstoneproject.tummyfit.data.repository.AuthRepositoryImpl
import com.capstoneproject.tummyfit.data.repository.FoodRepository
import com.capstoneproject.tummyfit.data.repository.FoodRepositoryImpl
import com.capstoneproject.tummyfit.data.repository.UserRepository
import com.capstoneproject.tummyfit.data.repository.UserRepositoryImpl
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
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideFoodRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository
}