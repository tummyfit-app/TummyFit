package com.capstoneproject.tummyfit.data.remote.model.auth

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: String? = null
)

data class Response(

	@field:SerializedName("message")
	val message: String? = null
)
