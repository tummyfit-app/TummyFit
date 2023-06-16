package com.capstoneproject.tummyfit.data.remote.service

import com.capstoneproject.tummyfit.data.remote.model.auth.LoginRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body registerRequestBody: RegisterRequestBody): RegisterResponse

    @PATCH("/api/auth")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body updateUserRequestBody: UpdateUserRequestBody
    ): UpdateUserResponse

    @Multipart
    @PATCH("/api/auth")
    suspend fun updatePhotoUser(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ): UpdateUserResponse
}