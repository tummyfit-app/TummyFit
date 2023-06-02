package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity
import com.capstoneproject.tummyfit.data.local.datasource.WaterIntakeLocalDataSource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface WaterIntakeRepository {
    suspend fun insertWaterIntake(waterIntakeEntity: WaterIntakeEntity)
    suspend fun getStatsWaterIntake(id: String, date: String): WaterIntakeEntity
    suspend fun updateIntake(now_intake: Int, id: String, date: String)
    suspend fun isUserExist(date: String, id_user: String): Boolean
}

class WaterIntakeRepositoryImpl @Inject constructor(private val waterIntakeLocalDataSource: WaterIntakeLocalDataSource) :
    WaterIntakeRepository {
    override suspend fun insertWaterIntake(waterIntakeEntity: WaterIntakeEntity) {
        if (!isUserExist(waterIntakeEntity.date, waterIntakeEntity.id_user)) {
            waterIntakeLocalDataSource.insertWaterIntake(waterIntakeEntity)
        }
    }

    override suspend fun getStatsWaterIntake(id: String, date: String): WaterIntakeEntity =
        waterIntakeLocalDataSource.getStatsWaterIntake(id, date)

    override suspend fun updateIntake(now_intake: Int, id: String, date: String) =
        waterIntakeLocalDataSource.updateIntake(now_intake, id, date)

    override suspend fun isUserExist(date: String, id_user: String): Boolean =
        waterIntakeLocalDataSource.isUserExist(date, id_user)

}