package com.capstoneproject.tummyfit.di

import android.content.Context
import com.capstoneproject.tummyfit.BuildConfig
import com.capstoneproject.tummyfit.data.remote.service.AuthApiService
import com.capstoneproject.tummyfit.data.remote.service.UserApiService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    //    @Provides
//    @Singleton
//    fun provideChucker(@ApplicationContext appContext: Context): ChuckerInterceptor =
//        ChuckerInterceptor.Builder(appContext).build()
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(
//        chuckerInterceptor: ChuckerInterceptor,
//    ): OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(chuckerInterceptor)
//        .connectTimeout(120, TimeUnit.SECONDS)
//        .readTimeout(120, TimeUnit.SECONDS)
//        .build()
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)
}