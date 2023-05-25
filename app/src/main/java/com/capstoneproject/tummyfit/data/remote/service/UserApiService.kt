package com.capstoneproject.tummyfit.data.remote.service

import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import retrofit2.http.*

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface UserApiService {

    @POST("/api/v1/users")
    suspend fun postUserDesc(
        @Header("Authorization") token: String,
        @Body postUserDescRequestBody: PostUserDescRequestBody
    ): PostUserDescResponse

    @GET("/api/v1/users")
    suspend fun getUserDesc(
        @Header("Authorization") token: String,
    ): GetUserResponse

}