package com.capstoneproject.tummyfit.data.remote.model.food

import com.google.gson.annotations.SerializedName

data class GetFoodDetailResponse(

	@field:SerializedName("data")
	val data: DataFood,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Food(

	@field:SerializedName("alcohol")
	val alcohol: String,

	@field:SerializedName("carbo")
	val carbo: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("instructions")
	val instructions: String,

	@field:SerializedName("calories")
	val calories: String,

	@field:SerializedName("ready_minutes")
	val readyMinutes: String,

	@field:SerializedName("dishType")
	val dishType: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("protein")
	val protein: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("fat")
	val fat: String,

	@field:SerializedName("vegetarian")
	val vegetarian: String,

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("halal")
	val halal: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("popular")
	val popular: String
)

data class DataFood(

	@field:SerializedName("Food")
	val food: Food
)
