package com.capstoneproject.tummyfit.data.remote.service

import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescResponse
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

    @PATCH("/api/v1/users/{id}")
    suspend fun updateUserDesc(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateUserDescRequestBody: UpdateUserDescRequestBody
    ): UpdateUserDescResponse

}