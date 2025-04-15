package dev.gearturner.wallit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About WallIT",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFE3F2FD)), // light blue background
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lorem ipsum dolor sit amet consectetur adipiscing elit. " +
                        "Elit quisque faucibus ex sapien vitae pellentesque sem. " +
                        "Sem placerat in id cursus mi pretium tellus. Tellus duis convallis tempus leo eu euismod sed. " +
                        "Sed diam una tempor pulvinar vivamus fringilla lacus. Lacus nec metus bibendum egestas iaculis massa nisl. " +
                        "Nisl malesuada lacinia integer nunc posuere ut hendrerit.",
                modifier = Modifier
                    .padding(24.dp),
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}
