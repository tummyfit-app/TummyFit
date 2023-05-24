package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.remote.datasource.UserRemoteDataSource
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import com.capstoneproject.tummyfit.data.remote.model.user.UserResponse
import com.capstoneproject.tummyfit.wrapper.Resource
import com.capstoneproject.tummyfit.wrapper.proceed
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
    ): Resource<UserResponse>
}

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {
    override suspend fun postUserDesc(
        token: String,
        postUserDescRequestBody: PostUserDescRequestBody
    ): Resource<PostUserDescResponse> =
        proceed { userRemoteDataSource.postUserDesc(token, postUserDescRequestBody) }

    override suspend fun getUserDesc(token: String): Resource<UserResponse> =
        proceed { userRemoteDataSource.getUserDesc(token) }
}