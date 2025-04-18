package dev.gearturner.wallit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.gearturner.wallit.model.Wallpaper
import dev.gearturner.wallit.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController,
    wallpapers: List<Wallpaper>,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
    ) {
        items(wallpapers) {
            val wallpaperId = it.id
            WallpaperCard(imageUrl = "https://picsum.photos/id/${wallpaperId}/1080/1920") {
                navController.navigate(route = Screens.DetailScreen.name + "/$wallpaperId")
            }
        }
    }
}

@Composable
fun WallpaperCard(imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(360.dp)
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}