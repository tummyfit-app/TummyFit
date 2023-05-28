package com.capstoneproject.tummyfit.data.remote.datasource

import com.capstoneproject.tummyfit.data.remote.model.auth.LoginRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserResponse
import com.capstoneproject.tummyfit.data.remote.service.AuthApiService
import retrofit2.http.Body
import retrofit2.http.Header
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface AuthRemoteDataSource {
    suspend fun login(loginRequestBody: LoginRequestBody): LoginResponse
    suspend fun register(registerRequestBody: RegisterRequestBody): RegisterResponse
    suspend fun updateUser(
        token: String,
        updateUserRequestBody: UpdateUserRequestBody
    ): UpdateUserResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiService) :
    AuthRemoteDataSource {
    override suspend fun login(loginRequestBody: LoginRequestBody): LoginResponse =
        apiService.login(loginRequestBody)

    override suspend fun register(registerRequestBody: RegisterRequestBody): RegisterResponse =
        apiService.register(registerRequestBody)

    override suspend fun updateUser(
        token: String,
        updateUserRequestBody: UpdateUserRequestBody
    ): UpdateUserResponse = apiService.updateUser(token, updateUserRequestBody)
}