package com.capstoneproject.tummyfit.data.remote.service

import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FoodApiService {

    @GET("/api/v1/foods")
    suspend fun getListFoods(
        @Header("Authorization") token: String
    ): GetFoodResponse

}