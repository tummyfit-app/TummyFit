package com.capstoneproject.tummyfit.data.remote.datasource

import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import com.capstoneproject.tummyfit.data.remote.service.FoodApiService
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FoodRemoteDataSource {
    suspend fun getListFoods(
        token: String
    ): GetFoodResponse

    suspend fun getDetailFood(
        token: String,
        id: String
    ): GetFoodDetailResponse

    suspend fun searchFoodsByCategory(
        token: String, q: String?, category: String?,
    ): GetFoodResponse
}

class FoodRemoteDataSourceImpl @Inject constructor(private val apiService: FoodApiService) :
    FoodRemoteDataSource {
    override suspend fun getListFoods(token: String): GetFoodResponse =
        apiService.getListFoods(token)

    override suspend fun getDetailFood(token: String, id: String): GetFoodDetailResponse =
        apiService.getDetailFood(token, id)

    override suspend fun searchFoodsByCategory(
        token: String,
        q: String?,
        category: String?
    ): GetFoodResponse = apiService.searchFoodsByCategory(token, q, category)
}