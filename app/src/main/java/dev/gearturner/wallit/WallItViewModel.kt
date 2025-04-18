package dev.gearturner.wallit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gearturner.wallit.model.Wallpaper
import dev.gearturner.wallit.network.WallItApi
import kotlinx.coroutines.launch

class WallItViewModel: ViewModel() {
    var wallpapers by mutableStateOf<List<Wallpaper>>(emptyList())
    var sampleWallpapers by mutableStateOf<List<Wallpaper>>(emptyList())

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
}