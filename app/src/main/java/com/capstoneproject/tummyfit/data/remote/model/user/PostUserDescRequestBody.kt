package com.capstoneproject.tummyfit.data.remote.model.user

import com.google.gson.annotations.SerializedName

data class PostUserDescRequestBody(

	@field:SerializedName("halal")
	val halal: String,

	@field:SerializedName("purpose")
	val purpose: String,

	@field:SerializedName("sex")
	val sex: String,

	@field:SerializedName("vegetarian")
	val vegetarian: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("vegan")
	val vegan: String,

	@field:SerializedName("birthDate")
	val birthDate: String,

	@field:SerializedName("gluten_free")
	val glutenFree: String,

	@field:SerializedName("dairy_free")
	val dairyFree: String,

	@field:SerializedName("daily_activity")
	val dailyActivity: String,

	@field:SerializedName("height")
	val height: Int
)
