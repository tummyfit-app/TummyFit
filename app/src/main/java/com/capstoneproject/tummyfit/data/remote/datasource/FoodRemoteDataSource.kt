package com.capstoneproject.tummyfit.data.remote.datasource

import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodPredictResponse
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

    suspend fun getDetailFood(
        token: String,
        id: String
    ): GetFoodDetailResponse

    suspend fun searchFoodsByCategory(
        token: String, q: String?, category: String?,
    ): GetFoodResponse

    suspend fun getFoodPredict(
        token: String
    ): GetFoodPredictResponse

    suspend fun recipeFoodsByCategory(
        token: String,
        popular: String?,
        halal: String?,
        price: String?,
        minutes: String?,
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

    override suspend fun getFoodPredict(token: String): GetFoodPredictResponse =
        apiService.getFoodPredict(token)

    override suspend fun recipeFoodsByCategory(
        token: String,
        popular: String?,
        halal: String?,
        price: String?,
        minutes: String?
    ): GetFoodResponse = apiService.recipeFoodsByCategory(token, popular, halal, price)
}