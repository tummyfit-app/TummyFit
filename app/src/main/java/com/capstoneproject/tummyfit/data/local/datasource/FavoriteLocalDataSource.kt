package com.capstoneproject.tummyfit.data.local.datasource

import com.capstoneproject.tummyfit.data.local.database.dao.FavoriteMealDao
import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FavoriteLocalDataSource {
    suspend fun addFavorite(favorite: FavoriteMealEntity)
    suspend fun removeFavorite(favorite: FavoriteMealEntity)
    suspend fun getAllFavorites(id: String): List<FavoriteMealEntity>
    suspend fun isFavorite(id: String, id_user: String): Boolean
}

class FavoriteLocalDataSourceImpl @Inject constructor(private val favoriteMealDao: FavoriteMealDao) :
    FavoriteLocalDataSource {
    override suspend fun addFavorite(favorite: FavoriteMealEntity) =
        favoriteMealDao.addFavorite(favorite)

    override suspend fun removeFavorite(favorite: FavoriteMealEntity) =
        favoriteMealDao.removeFavorite(favorite)

    override suspend fun getAllFavorites(id: String): List<FavoriteMealEntity> =
        favoriteMealDao.getAllFavorites(id)

    override suspend fun isFavorite(id: String, id_user: String): Boolean = favoriteMealDao.isFavorite(id, id_user)

}