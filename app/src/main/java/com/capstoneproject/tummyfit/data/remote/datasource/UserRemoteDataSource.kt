package com.capstoneproject.tummyfit.data.remote.datasource

import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.PostUserDescResponse
import com.capstoneproject.tummyfit.data.remote.model.user.UserResponse
import com.capstoneproject.tummyfit.data.remote.service.UserApiService
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface UserRemoteDataSource {
    suspend fun postUserDesc(
        token: String,
        postUserDescRequestBody: PostUserDescRequestBody
    ): PostUserDescResponse

    suspend fun getUserDesc(
        token: String,
    ): UserResponse
}

class UserRemoteDataSourceImpl @Inject constructor(private val apiService: UserApiService) :
    UserRemoteDataSource {
    override suspend fun postUserDesc(
        token: String,
        postUserDescRequestBody: PostUserDescRequestBody
    ): PostUserDescResponse = apiService.postUserDesc(token, postUserDescRequestBody)

    override suspend fun getUserDesc(token: String): UserResponse = apiService.getUserDesc(token)

}