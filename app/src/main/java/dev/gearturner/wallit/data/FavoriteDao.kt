/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
database access object for WallItDatabase
 */

package dev.gearturner.wallit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.gearturner.wallit.model.Favorite

@Dao
interface FavoriteDao{
    //Inserts a Favorite into the database
    //If the entry already exists with the same primary key, it will be replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<Favorite>

    //Retrieves a specific Favorite by its 'api_id'
    //Returns null if not found
    @Query("SELECT * FROM favorites WHERE api_id = :id")
    suspend fun getFavorite(id: Int): Favorite?
}