package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.remote.datasource.FoodRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodPredictResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import com.capstoneproject.tummyfit.wrapper.Resource
import com.capstoneproject.tummyfit.wrapper.proceed
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FoodRepository {
    suspend fun getListFoods(
        token: String
    ): Resource<GetFoodResponse>

    suspend fun getDetailFood(
        token: String,
        id: String
    ): Resource<GetFoodDetailResponse>

    suspend fun searchFoodsByCategory(
        token: String, q: String?, category: String?,
    ): Resource<GetFoodResponse>

    suspend fun getFoodPredict(
        token: String
    ): Resource<GetFoodPredictResponse>

    suspend fun recipeFoodsByCategory(
        token: String,
        popular: String?,
        halal: String?,
        price: String?,
        minutes: String?,
    ): Resource<GetFoodResponse>
}

class FoodRepositoryImpl @Inject constructor(private val foodRemoteDataSource: FoodRemoteDataSource) :
    FoodRepository {
    override suspend fun getListFoods(token: String): Resource<GetFoodResponse> =
        proceed {
            foodRemoteDataSource.getListFoods(token)
        }

    override suspend fun getDetailFood(token: String, id: String): Resource<GetFoodDetailResponse> =
        proceed { foodRemoteDataSource.getDetailFood(token, id) }

    override suspend fun searchFoodsByCategory(
        token: String,
        q: String?,
        category: String?
    ): Resource<GetFoodResponse> =
        proceed { foodRemoteDataSource.searchFoodsByCategory(token, q, category) }

    override suspend fun getFoodPredict(token: String): Resource<GetFoodPredictResponse> = proceed {
        foodRemoteDataSource.getFoodPredict(token)
    }

    override suspend fun recipeFoodsByCategory(
        token: String,
        popular: String?,
        halal: String?,
        price: String?,
        minutes: String?
    ): Resource<GetFoodResponse> = proceed {
        foodRemoteDataSource.recipeFoodsByCategory(token, popular, halal, price, minutes)
    }
}