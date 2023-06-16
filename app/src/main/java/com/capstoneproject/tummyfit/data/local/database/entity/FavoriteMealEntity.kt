package com.capstoneproject.tummyfit.data.local.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Entity(tableName = "favorite_meal")
@Parcelize
data class FavoriteMealEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_meal")
    val id_meal: String,

    @ColumnInfo(name = "name_meal")
    val name: String,

    @ColumnInfo(name = "image_meal")
    val image: String,

    @ColumnInfo(name = "kcal_meal")
    val kcal: String,

    @ColumnInfo(name = "desc_meal")
    val desc: String,

    @ColumnInfo(name = "id_user")
    val id_user: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
) : Parcelable