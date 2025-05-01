package dev.gearturner.wallit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Marks this data class as a Room entity representing a table named "favorites"
@Entity(tableName = "favorites")
data class Favorite(

    //Declares 'api_id' as the primary key for the "favorites" table
    @PrimaryKey val api_id: Int,
)