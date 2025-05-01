package dev.gearturner.wallit.screens

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.gearturner.wallit.WallItViewModel
import dev.gearturner.wallit.model.Wallpaper

@SuppressLint("UnrememberedMutableState") //Suppresses warning about not using remember in favorited state
@Composable
fun DetailScreen(
    wallpaper: Wallpaper,  //The selected wallpaper to display
    viewModel: WallItViewModel  //ViewModel for managing favorites and state
) {
    //Construct high-resolution image URL
    val imageUrl = "https://picsum.photos/id/${wallpaper.id}/1080/1920"
    val author = wallpaper.author

    //Check if the current wallpaper is in favorites when the screen loads
    LaunchedEffect(wallpaper.id) {
        viewModel.isFavorite(wallpaper)
    }

    //Current favorite state for the wallpaper
    val isFavorite = viewModel.favorited

    //Get context to use for download manager
    val context = LocalContext.current
    val fileName = "WALLit_${wallpaper.id}.jpg"

    //Main column container for screen layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Card container for content styling and elevation
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFCFFFF9)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Display the wallpaper image using Coil's AsyncImage
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(340.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

                //Display the author's name
                Text(
                    text = "Author: $author",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
                ) {
                    Button(onClick = {
                        if (isFavorite) {
                            viewModel.removeFavorite(wallpaper)
                            viewModel.favorited = false
                            viewModel.favoritesNeedRefresh = true
                        } else {
                            viewModel.addFavorite(wallpaper)
                            viewModel.favorited = true
                            viewModel.favoritesNeedRefresh = true
                        }
                    }) {
                        Icon(
                            imageVector = if(!isFavorite) Icons.Outlined.FavoriteBorder else Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }

                    //Button button using Android's DownloadManager
                    Button(onClick = {
                        val request = DownloadManager.Request(Uri.parse(imageUrl)).apply {
                            setTitle("Downloading wallpaper...")
                            setDescription("Saving $fileName...")
                            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)
                            setAllowedOverMetered(true)
                            setAllowedOverRoaming(true)
                        }

                        //Enqueue the download request
                        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        downloadManager.enqueue(request)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Download,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}