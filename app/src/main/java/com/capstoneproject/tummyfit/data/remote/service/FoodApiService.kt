package com.capstoneproject.tummyfit.data.remote.service

import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodDetailResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodPredictResponse
import com.capstoneproject.tummyfit.data.remote.model.food.GetFoodResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
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

    @GET("/api/v1/foods/predict?")
    suspend fun getFoodPredict(
        @Header("Authorization") token: String,
        @Query("day") day: String? = null,
    ): GetFoodPredictResponse

    @GET("/api/v1/foods?")
    suspend fun searchFoodsByCategory(
        @Header("Authorization") token: String,
        @Query("name") q: String? = null,
        @Query("dishtype") category: String? = null,
    ): GetFoodResponse

    @GET("api/v1/foods/{id}")
    suspend fun getDetailFood(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): GetFoodDetailResponse

    @GET("/api/v1/foods?")
    suspend fun recipeFoodsByCategory(
        @Header("Authorization") token: String,
        @Query("popular") popular: String? = null,
        @Query("halal") halal: String? = null,
        @Query("price") price: String? = null,
        @Query("minutes") minutes: String? = null,
    ): GetFoodResponse
}