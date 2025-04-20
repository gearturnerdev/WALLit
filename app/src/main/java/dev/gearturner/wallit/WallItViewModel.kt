package dev.gearturner.wallit

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.gearturner.wallit.data.WallItDatabase
import dev.gearturner.wallit.model.Wallpaper
import dev.gearturner.wallit.network.WallItApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class WallItViewModel(application: Application): AndroidViewModel(application) {
    var wallpapers by mutableStateOf<List<Wallpaper>>(emptyList())
    var sampleWallpapers by mutableStateOf<List<Wallpaper>>(emptyList())
    val favorites = mutableStateOf<Wallpaper>()

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

    fun loadSampleWallpapers() {
        viewModelScope.launch {
            val wallpaperIds = listOf(0, 1, 2)

            val loadedWallpapers = wallpaperIds.mapNotNull {
                try {
                    WallItApi.retrofitService.getWallpaperInfo(it)
                } catch (e: Exception) { null }
            }
            sampleWallpapers = loadedWallpapers
        }
    }
    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favs = favoriteDao.getAllFavorites()
            val wallpaperList = favs.map {
                Wallpaper(
                    id = it.api_id.toString(),
                    author = it.author,
                    download_url = "https://picsum.photos/id/${it.api_id}/1080/1920",
                    url = "",
                    width = 0,
                    height = 0
                )
            }
            favorites.clear()
            favorites.addAll(wallpaperList)
        }
    }

    fun addFavorite(wallpaper: Wallpaper) {
        val favorite = Favorite(
            api_id = wallpaper.id.toInt(),
            author = wallpaper.author
        )
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.insertFavorite(favorite)
        }
    }
}