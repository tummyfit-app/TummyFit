package com.capstoneproject.tummyfit.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: String,

	@field:SerializedName("token")
	val token: String
)
