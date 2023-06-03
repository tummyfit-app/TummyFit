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

@Entity(tableName = "water_intake")
@Parcelize
data class WaterIntakeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "id_user")
    val id_user: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "now_intake")
    val now_intake: Int,

    @ColumnInfo(name = "total_intake")
    val total_intake: Int,
) : Parcelable