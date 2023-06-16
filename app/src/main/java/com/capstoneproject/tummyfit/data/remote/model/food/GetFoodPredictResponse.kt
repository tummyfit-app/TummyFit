package com.capstoneproject.tummyfit.data.remote.model.food

import com.google.gson.annotations.SerializedName

data class GetFoodPredictResponse(

	@field:SerializedName("data")
	val data: DataMeals,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("statusCode")
	val statusCode: String
)

data class MealItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("dateUpdatedUser")
	val dateUpdatedUser: String,

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("calories")
	val calories: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("day")
	val day: String,

	@field:SerializedName("userId")
	val userId: String
)

data class DataMeals(

	@field:SerializedName("Calorie")
	val calorie: Int,

	@field:SerializedName("Meal")
	val meal: List<MealItem>
)
