package com.capstoneproject.tummyfit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstoneproject.tummyfit.data.local.database.dao.FavoriteMealDao
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Database(entities = [FavoriteMealEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

 abstract fun favoriteMealDao(): FavoriteMealDao
}