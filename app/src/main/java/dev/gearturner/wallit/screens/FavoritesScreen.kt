package dev.gearturner.wallit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gearturner.wallit.model.Wallpaper
import dev.gearturner.wallit.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    wallpapers: List<Wallpaper>,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        items(wallpapers) {
            val wallpaperId = it.id
            WallpaperCard(imageUrl = "https://picsum.photos/id/${wallpaperId}/1080/1920") {
                navController.navigate(route = Screens.DetailScreen.name + "/$wallpaperId")
            }
        }
    }
}