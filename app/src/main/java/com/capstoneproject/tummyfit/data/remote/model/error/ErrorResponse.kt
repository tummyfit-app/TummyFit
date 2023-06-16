package com.capstoneproject.tummyfit.data.remote.model.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: String
)
