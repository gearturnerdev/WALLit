package dev.gearturner.wallit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.gearturner.wallit.model.Favorite

@Dao
interface FavoriteDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE api_id = :id")
    suspend fun getFavorite(id: Int): Favorite?
}