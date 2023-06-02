package com.capstoneproject.tummyfit.data.local.datasource

import androidx.room.Insert
import androidx.room.Query
import com.capstoneproject.tummyfit.data.local.database.dao.WaterIntakeDao
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface WaterIntakeLocalDataSource {
    suspend fun insertWaterIntake(waterIntakeEntity: WaterIntakeEntity)
    suspend fun getStatsWaterIntake(id: String, date: String): WaterIntakeEntity
    suspend fun updateIntake(now_intake: Int, id: String, date: String)
    suspend fun isUserExist(date: String, id_user: String): Boolean
    suspend fun getListWaterIntake(id: String): List<WaterIntakeEntity>
}

class WaterIntakeLocalDataSourceImpl @Inject constructor(private val waterIntakeDao: WaterIntakeDao) :
    WaterIntakeLocalDataSource {
    override suspend fun insertWaterIntake(waterIntakeEntity: WaterIntakeEntity) =
        waterIntakeDao.insertWaterIntake(waterIntakeEntity)

    override suspend fun getStatsWaterIntake(id: String, date: String): WaterIntakeEntity =
        waterIntakeDao.getStatsWaterIntake(id, date)

    override suspend fun updateIntake(now_intake: Int, id: String, date: String) =
        waterIntakeDao.updateIntake(now_intake, id, date)

    override suspend fun isUserExist(date: String, id_user: String): Boolean =
        waterIntakeDao.isUserExist(date, id_user)

    override suspend fun getListWaterIntake(id: String): List<WaterIntakeEntity> =
        waterIntakeDao.getListWaterIntake(id)

}