package com.capstoneproject.tummyfit.data.remote.datasource

import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import com.capstoneproject.tummyfit.data.remote.service.FoodApiService
import retrofit2.http.Header
import retrofit2.http.Path
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

    suspend fun searchFoods(token: String, name: String): GetFoodResponse

    suspend fun getDetailFood(
        token: String,
        id: String
    ): GetFoodDetailResponse
}

class FoodRemoteDataSourceImpl @Inject constructor(private val apiService: FoodApiService) :
    FoodRemoteDataSource {
    override suspend fun getListFoods(token: String): GetFoodResponse =
        apiService.getListFoods(token)

    override suspend fun searchFoods(token: String, name: String): GetFoodResponse =
        apiService.searchFoods(token, name)

    override suspend fun getDetailFood(token: String, id: String): GetFoodDetailResponse =
        apiService.getDetailFood(token, id)
}