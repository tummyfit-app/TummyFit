package com.capstoneproject.tummyfit.data.remote.model.user

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class UserDescription(
    @field:SerializedName("id")
    val id: String,

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

    @field:SerializedName("gluten_free")
    val glutenFree: String,

    @field:SerializedName("daily_activity")
    val dailyActivity: String,

    @field:SerializedName("dairy_free")
    val dairyFree: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("height")
    val height: Int
)

data class User(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)

data class Data(

    @field:SerializedName("UserDescription")
    val userDescription: UserDescription
)
