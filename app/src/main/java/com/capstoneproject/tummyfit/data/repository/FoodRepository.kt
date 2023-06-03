package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.remote.datasource.FoodRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
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
}