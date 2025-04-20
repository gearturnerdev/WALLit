package dev.gearturner.wallit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.gearturner.wallit.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class WallItDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: WallItDatabase?= null

        fun getDatabase(context: Context): WallItDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WallItDatabase::class.java,
                    "wallit_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
