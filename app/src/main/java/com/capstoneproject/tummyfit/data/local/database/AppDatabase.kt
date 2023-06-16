package com.capstoneproject.tummyfit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstoneproject.tummyfit.data.local.database.dao.FavoriteMealDao
import com.capstoneproject.tummyfit.data.local.database.dao.WaterIntakeDao
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.local.database.entity.WaterIntakeEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Database(
    entities = [FavoriteMealEntity::class, WaterIntakeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMealDao(): FavoriteMealDao
    abstract fun waterIntakeDao(): WaterIntakeDao
}