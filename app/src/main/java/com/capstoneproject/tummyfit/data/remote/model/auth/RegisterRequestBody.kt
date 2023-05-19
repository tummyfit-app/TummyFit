package com.capstoneproject.tummyfit.data.remote.model.auth

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

data class RegisterRequestBody(
    val email: String,
    val username: String,
    val password: String
)
