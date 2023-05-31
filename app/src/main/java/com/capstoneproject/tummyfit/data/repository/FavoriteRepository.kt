package com.capstoneproject.tummyfit.data.repository

import com.capstoneproject.tummyfit.data.local.database.entity.FavoriteMealEntity
import com.capstoneproject.tummyfit.data.local.datasource.FavoriteLocalDataSource
import com.capstoneproject.tummyfit.wrapper.Resource
import javax.inject.Inject

/**
 * @Author: ridhogymnastiar
 * Github: https://github.com/ridhogaa
 */

interface FavoriteRepository {
    suspend fun addFavorite(favorite: FavoriteMealEntity)
    suspend fun removeFavorite(favorite: FavoriteMealEntity)
    suspend fun getAllFavorites(id: String): Resource<List<FavoriteMealEntity>>
    suspend fun isFavorite(id: String, id_user: String): Boolean
}

class FavoriteRepositoryImpl @Inject constructor(private val favoriteLocalDataSource: FavoriteLocalDataSource) :
    FavoriteRepository {
    override suspend fun addFavorite(favorite: FavoriteMealEntity) =
        favoriteLocalDataSource.addFavorite(favorite)

    override suspend fun removeFavorite(favorite: FavoriteMealEntity) =
        favoriteLocalDataSource.removeFavorite(favorite)

    override suspend fun getAllFavorites(id: String): Resource<List<FavoriteMealEntity>> = proceed {
        favoriteLocalDataSource.getAllFavorites(id).map {
            FavoriteMealEntity(
                it.id_meal,
                it.name,
                it.image,
                it.kcal,
                it.desc,
                it.id_user,
                it.isFavorite
            )
        }
    }

    override suspend fun isFavorite(id: String, id_user: String): Boolean =
        favoriteLocalDataSource.isFavorite(id, id_user)

    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception, exception.message)
        }
    }
}