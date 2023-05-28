package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.remote.datasource.FoodRemoteDataSource
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
}

class FoodRepositoryImpl @Inject constructor(private val foodRemoteDataSource: FoodRemoteDataSource) :
    FoodRepository {
    override suspend fun getListFoods(token: String): Resource<GetFoodResponse> =
        proceed {
            foodRemoteDataSource.getListFoods(token)
        }

}