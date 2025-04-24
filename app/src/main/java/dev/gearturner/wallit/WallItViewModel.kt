package dev.gearturner.wallit

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.gearturner.wallit.data.WallItDatabase
import dev.gearturner.wallit.model.Favorite
import dev.gearturner.wallit.model.Wallpaper
import dev.gearturner.wallit.network.WallItApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WallItViewModel(application: Application): AndroidViewModel(application) {
    var wallpapers by mutableStateOf<List<Wallpaper>>(emptyList())
    var favoriteWallpapers by mutableStateOf<List<Wallpaper>>(emptyList())
    var favorited by mutableStateOf(false)
    private val favoriteDao = WallItDatabase.getDatabase(application).favoriteDao()

    fun loadWallpapers() {
        viewModelScope.launch {
            val wallpaperIds = (0..1084).toList()
            val randomIds = wallpaperIds.shuffled().take(25)

            val loadedWallpapers = randomIds.mapNotNull {
                try {
                    WallItApi.retrofitService.getWallpaperInfo(it)
                } catch (e: Exception) { null }
            }
            wallpapers = loadedWallpapers
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val favorites = favoriteDao.getAllFavorites()

            val loadedFavorites = favorites.mapNotNull { fav ->
                try {
                    WallItApi.retrofitService.getWallpaperInfo(fav.api_id)
                } catch (e: Exception) { null }
            }
            favoriteWallpapers = loadedFavorites
        }
    }

    fun addFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = Favorite(
                api_id = wallpaper.id.toInt()
            )
            favoriteDao.insertFavorite(favorite)
        }
    }

    fun removeFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = Favorite(
                api_id = wallpaper.id.toInt()
            )
            favoriteDao.deleteFavorite(favorite)
        }
    }

    fun isFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch {
            favorited = favoriteDao.getFavorite(wallpaper.id.toInt()) != null
        }
    }
}