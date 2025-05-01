/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
Room entity for favorites table
 */

package dev.gearturner.wallit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    //Declares api_id as the primary key for the table
    @PrimaryKey val api_id: Int,
)