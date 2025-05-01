/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
database to hold wallpapers marked as favorite
 */

package dev.gearturner.wallit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.gearturner.wallit.model.Favorite

//Defines the Room database configuration
//'entities' lists all the data models (tables), and 'version' is the schema version
@Database(entities = [Favorite::class], version = 1)
abstract class WallItDatabase: RoomDatabase() {

    //Abstract function that provides access to the DAO (Data Access Object)
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        //Volatile ensures the value of INSTANCE is always up to date and visible to all threads
        @Volatile
        private var INSTANCE: WallItDatabase?= null

        //Singleton pattern to get a single instance
        fun getDatabase(context: Context): WallItDatabase {
            //Return the existing instance or create it if it doesn't exist
            return INSTANCE ?: synchronized(this) {
                //Synchronized block ensures only one thread can access this at a time
                val instance = Room.databaseBuilder(
                    context.applicationContext,  //Use application context to avoid memory
                    WallItDatabase::class.java,  //Reference to the database class
                    "wallit_database"            //Name of the database file
                ).build()
                INSTANCE = instance     //Save the instance
                return instance         //Return the instance
            }
        }
    }
}
