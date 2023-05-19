package com.capstoneproject.tummyfit.data.remote.service

import com.capstoneproject.tummyfit.data.remote.model.auth.LoginRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequestBody: LoginRequestBody): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body registerRequestBody: RegisterRequestBody): RegisterResponse

}