package com.capstoneproject.tummyfit.data.remote.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: DataGet,

	@field:SerializedName("status")
	val status: String
)

data class DataGet(

	@field:SerializedName("UserDescription")
	val userDescription: UserDescriptionGet
)

data class User(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("namauser")
	val namauser: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class UserDescriptionGet(

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

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("height")
	val height: Int
)
