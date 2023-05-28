package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.remote.datasource.UserRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.GetUserResponse
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.UpdateUserDescResponse
import com.capstoneproject.tummyfit.wrapper.Resource
import com.capstoneproject.tummyfit.wrapper.proceed
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface UserRepository {
    suspend fun postUserDesc(
        token: String,
        postUserDescRequestBody: PostUserDescRequestBody
    ): Resource<PostUserDescResponse>

    suspend fun getUserDesc(
        token: String,
    ): Resource<GetUserResponse>

    suspend fun updateUserDesc(
        token: String,
        id: String,
        updateUserDescRequestBody: UpdateUserDescRequestBody
    ): Resource<UpdateUserDescResponse>
}

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {
    override suspend fun postUserDesc(
        token: String,
        postUserDescRequestBody: PostUserDescRequestBody
    ): Resource<PostUserDescResponse> =
        proceed { userRemoteDataSource.postUserDesc(token, postUserDescRequestBody) }

    override suspend fun getUserDesc(token: String): Resource<GetUserResponse> =
        proceed { userRemoteDataSource.getUserDesc(token) }

    override suspend fun updateUserDesc(
        token: String,
        id: String,
        updateUserDescRequestBody: UpdateUserDescRequestBody
    ): Resource<UpdateUserDescResponse> =
        proceed { userRemoteDataSource.updateUserDesc(token, id, updateUserDescRequestBody) }
}