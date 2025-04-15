package dev.gearturner.wallit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    author: String,
    size: String,
    imageUrl: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wallpaper Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // or use Color.Blue
                    titleContentColor = MaterialTheme.colorScheme.onPrimary)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
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
                        contentDescription = title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(550.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = title, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Author: $author", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Size: $size", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(onClick = { /* Add to favorites logic */ }) {
                        Text("Add to Favorites")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        title = "Mountain Sunset",
        author = "John Doe",
        size = "1080x1920",
        imageUrl = "https://picsum.photos/500/300"
    )
}
