package com.capstoneproject.tummyfit.data.remote.model.auth

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

data class RegisterRequestBody(
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String
)
