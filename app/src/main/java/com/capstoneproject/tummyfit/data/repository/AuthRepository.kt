package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.local.datasource.AuthLocalDataSource
import com.capstoneproject.tummyfit.data.remote.datasource.AuthRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.LoginResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.RegisterResponse
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserResponse
import com.capstoneproject.tummyfit.wrapper.Resource
import com.capstoneproject.tummyfit.wrapper.proceed
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface AuthRepository {
    fun getToken(): Flow<String>
    suspend fun setToken(token: String)
    suspend fun clear()
    fun getId(): Flow<String>
    suspend fun setId(id: String)
    suspend fun clearId()
    suspend fun login(loginRequestBody: LoginRequestBody): Resource<LoginResponse>
    suspend fun register(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse>
    suspend fun updateUser(
        token: String,
        updateUserRequestBody: UpdateUserRequestBody
    ): Resource<UpdateUserResponse>

    suspend fun updatePhotoUser(
        token: String,
        file: MultipartBody.Part,
    ): Resource<UpdateUserResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun getToken(): Flow<String> = authLocalDataSource.getToken()

    override suspend fun setToken(token: String) = authLocalDataSource.setToken(token)

    override suspend fun clear() = authLocalDataSource.clearToken()
    override fun getId(): Flow<String> = authLocalDataSource.getId()

    override suspend fun setId(id: String) = authLocalDataSource.setId(id)

    override suspend fun clearId() = authLocalDataSource.clearId()

    override suspend fun login(loginRequestBody: LoginRequestBody): Resource<LoginResponse> {
        val response = proceed { authRemoteDataSource.login(loginRequestBody) }
        response.payload?.token?.let { authLocalDataSource.setToken(it) }
        return response
    }

    override suspend fun register(registerRequestBody: RegisterRequestBody): Resource<RegisterResponse> =
        proceed {
            authRemoteDataSource.register(registerRequestBody)
        }

    override suspend fun updateUser(
        token: String,
        updateUserRequestBody: UpdateUserRequestBody
    ): Resource<UpdateUserResponse> =
        proceed {
            authRemoteDataSource.updateUser(
                token,
                updateUserRequestBody
            )
        }

    override suspend fun updatePhotoUser(
        token: String,
        file: MultipartBody.Part
    ): Resource<UpdateUserResponse> = proceed {
        authRemoteDataSource.updatePhotoUser(token, file)
    }

}