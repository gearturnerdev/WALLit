package dev.gearturner.wallit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import dev.gearturner.wallit.ui.theme.WALLitTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String = "Mountain sunset",
    author: String = "John D.",
    size: String = "1920x1080",
    imageUrl: String = "https://picsum.photos/500/300",
    navController: NavController
) {
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
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(480.dp)
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