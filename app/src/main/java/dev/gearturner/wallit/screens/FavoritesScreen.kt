/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
display list of favorited wallpapers
 */

package dev.gearturner.wallit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gearturner.wallit.model.Wallpaper
import dev.gearturner.wallit.navigation.Screens

//horizontal list of favorited wallpapers
@Composable
fun FavoritesScreen(
    navController: NavController,  //NavController used for screen navigation
    wallpapers: List<Wallpaper>,   //List of favorited wallpaper objects
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
    ) {
        items(wallpapers) {
            val wallpaperId = it.id
            WallpaperCard(imageUrl = "https://picsum.photos/id/${wallpaperId}/1080/1920") {
                navController.navigate(route = Screens.DetailScreen.name + "/$wallpaperId") //navigate to appropriate DetailScreen
            }
        }
    }
}