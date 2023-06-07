package com.capstoneproject.tummyfit.data.remote.model.food

import com.google.gson.annotations.SerializedName

data class GetFoodPredictResponse(

	@field:SerializedName("Prediction")
	val prediction: List<PredictionItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class MenuItem(

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Vegetarian")
	val vegetarian: String,

	@field:SerializedName("Ingredients")
	val ingredients: String,

	@field:SerializedName("Gluten Free")
	val glutenFree: String,

	@field:SerializedName("Dairy Free")
	val dairyFree: String,

	@field:SerializedName("Recipe Title")
	val recipeTitle: String,

	@field:SerializedName("Halal")
	val halal: String,

	@field:SerializedName("Instructions")
	val instructions: String,

	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("Calories")
	val calories: Double,

	@field:SerializedName("Vegan")
	val vegan: String
)

data class PredictionItem(

	@field:SerializedName("Menu")
	val menu: List<MenuItem>,

	@field:SerializedName("Total Calories")
	val totalCalories: Int,

	@field:SerializedName("Day")
	val day: String,

	@field:SerializedName("requirement")
	val requirement: Int
)
