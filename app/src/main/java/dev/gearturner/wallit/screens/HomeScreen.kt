package dev.gearturner.wallit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.gearturner.wallit.navigation.Screens
import dev.gearturner.wallit.ui.theme.WALLitTheme

@Composable
fun HomeScreen(
    navController: NavController,
    itemClick: () -> Unit = { navController.navigate(route = Screens.DetailScreen.name) }
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
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

@Composable
fun WallpaperCard(title: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(modifier = Modifier.padding(130.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}