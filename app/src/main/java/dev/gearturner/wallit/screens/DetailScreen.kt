package dev.gearturner.wallit.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.gearturner.wallit.WallItViewModel
import dev.gearturner.wallit.model.Wallpaper

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    wallpaper: Wallpaper,
    viewModel: WallItViewModel
) {
    val imageUrl = "https://picsum.photos/id/${wallpaper.id}/1080/1920"
    val author = wallpaper.author

    LaunchedEffect(wallpaper.id) {
        viewModel.isFavorite(wallpaper)
    }

    val isFavorite = viewModel.favorited

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(340.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Text(
                    text = "Author: $author",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(16.dp)
                )

                Button(onClick = {
                    if(isFavorite) {
                        viewModel.removeFavorite(wallpaper)
                        viewModel.favorited = false
                    } else {
                        viewModel.addFavorite(wallpaper)
                        viewModel.favorited = true
                    }
                }) {
                    Text(
                        if (!isFavorite) "Add to Favorites" else "Remove from Favorites"
                    )
                }
            }
        }
    }
}