package dev.gearturner.wallit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.gearturner.wallit.navigation.WallItNavigation
import dev.gearturner.wallit.ui.theme.WALLitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { // start app with ViewModel and the main WeatherNavigation composable
            App { WallItNavigation() }
        }
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    WALLitTheme {
        content()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    App { WallItNavigation() }
}