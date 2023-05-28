package com.capstoneproject.tummyfit.data.remote.datasource

import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import com.capstoneproject.tummyfit.data.remote.service.FoodApiService
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FoodRemoteDataSource {
    suspend fun getListFoods(
        token: String
    ): GetFoodResponse
}

class FoodRemoteDataSourceImpl @Inject constructor(private val apiService: FoodApiService) :
    FoodRemoteDataSource {
    override suspend fun getListFoods(token: String): GetFoodResponse =
        apiService.getListFoods(token)
}