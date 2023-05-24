package com.capstoneproject.tummyfit.data.remote.model.user

import com.google.gson.annotations.SerializedName

data class PostUserDescResponse(

	@field:SerializedName("data")
	val data: DataPost,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: String
)

data class UserDescriptionPost(

	@field:SerializedName("alcohol")
	val alcohol: String,

	@field:SerializedName("purpose")
	val purpose: String,

	@field:SerializedName("sex")
	val sex: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("vegan")
	val vegan: String,

	@field:SerializedName("birthDate")
	val birthDate: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("vegetarian")
	val vegetarian: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("gluten_free")
	val glutenFree: String,

	@field:SerializedName("dairy_free")
	val dairyFree: String,

	@field:SerializedName("daily_activity")
	val dailyActivity: String,

	@field:SerializedName("height")
	val height: Int
)

data class DataPost(

	@field:SerializedName("UserDescription")
	val userDescription: UserDescriptionPost
)
