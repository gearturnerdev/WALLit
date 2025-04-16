package dev.gearturner.wallit.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.gearturner.wallit.navigation.Screens
import dev.gearturner.wallit.ui.theme.WALLitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    itemClick: () -> Unit = { navController.navigate(route = Screens.DetailScreen.name) }
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            WallpaperCard(
                title = "Nature",
                imageUrl = "https://picsum.photos/200/300?random=1",
                onClick = itemClick
            )
        }
        item {
            WallpaperCard(
                title = "Cityscape",
                imageUrl = "https://picsum.photos/200/300?random=2",
                onClick = itemClick
            )
        }
        item {
            WallpaperCard(
                title = "Mountains",
                imageUrl = "https://picsum.photos/200/300?random=3",
                onClick = itemClick
            )
        }
    }
}