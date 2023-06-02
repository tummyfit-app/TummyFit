package com.capstoneproject.tummyfit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Dao
interface WaterIntakeDao {
    @Insert
    suspend fun insertWaterIntake(waterIntakeEntity: WaterIntakeEntity)

    @Query("SELECT * FROM water_intake WHERE id_user=:id AND date=:date")
    suspend fun getStatsWaterIntake(id: String, date: String): WaterIntakeEntity

    @Query("UPDATE water_intake SET now_intake= now_intake+:now_intake WHERE id_user=:id AND date=:date")
    suspend fun updateIntake(now_intake: Int, id: String, date: String)

    @Query("SELECT EXISTS(SELECT id_user, date FROM water_intake WHERE date = :date AND id_user = :id_user)")
    suspend fun isUserExist(date: String, id_user: String): Boolean

    @Query("SELECT * FROM water_intake WHERE id_user=:id")
    suspend fun getListWaterIntake(id: String): List<WaterIntakeEntity>
}