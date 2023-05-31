package com.capstoneproject.tummyfit.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

@Dao
interface FavoriteMealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteMealEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteMealEntity)

    @Query("SELECT * FROM favorite_meal WHERE id_user=:id")
    suspend fun getAllFavorites(id: String): List<FavoriteMealEntity>

    @Query("SELECT EXISTS(SELECT id_meal FROM favorite_meal WHERE id_meal = :id AND id_user = :id_user)")
    suspend fun isFavorite(id: String, id_user: String): Boolean
}