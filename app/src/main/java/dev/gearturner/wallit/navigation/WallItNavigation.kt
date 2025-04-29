package dev.gearturner.wallit.navigation

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.gearturner.wallit.WallItViewModel
import dev.gearturner.wallit.screens.DetailScreen
import dev.gearturner.wallit.screens.FavoritesScreen
import dev.gearturner.wallit.screens.HomeScreen
import dev.gearturner.wallit.screens.InfoScreen
import dev.gearturner.wallit.screens.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    title: String,
    containerColor: Color,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onMenuClick: (() -> Unit)? = null,
    modifier: Modifier,
    onShare: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = containerColor
        ),
        actions = { // share button
            if(onShare != null) {
                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier,
        navigationIcon = { // show back button if not on HomeScreen
            if(canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null
                    )
                }
            } else if(onMenuClick != null) { // hamburger menu otherwise
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

// navigation ui and logic
@Composable
fun WallItNavigation(viewModel: WallItViewModel) {
    // track navigation and variables
    val navController = rememberNavController()
    val viewModel = viewModel
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen = Screens.fromRoute(currentBackStackEntry?.destination?.route)
    val canNavigateBack = currentScreen == Screens.DetailScreen
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title = when(currentScreen) {
        Screens.HomeScreen -> "WALLit"
        Screens.DetailScreen -> "Wallpaper"
        Screens.FavoritesScreen -> "Favorites"
        Screens.InfoScreen -> "Info"
        Screens.SettingsScreen -> "Settings"
        else -> "This text should not appear..."
    }
    var currentSelection by remember { mutableStateOf("Home") }
    val context = LocalContext.current
    val defaultAccentColor = Color(0xFF7CD3C5)
    val altAccentColor = MaterialTheme.colorScheme.primaryContainer
    var accentColor by remember { mutableStateOf(defaultAccentColor) }
    val toggleAccentColor = {
        accentColor = if(accentColor == defaultAccentColor) altAccentColor else defaultAccentColor
    }

    // Scaffold that surrounds the screens, contains TopAppBar
    val scaffoldBody = @Composable {
        Scaffold(
            topBar = {
                NavBar(
                    title = title,
                    containerColor = accentColor,
                    canNavigateBack = canNavigateBack,
                    navigateUp = { navController.navigateUp() },
                    modifier = Modifier,
                    onMenuClick = { scope.launch { drawerState.open() } },

                    // share logic
                    onShare = if(currentScreen == Screens.DetailScreen) {
                        {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Check out this awesome wallpaper I found on WALLit!")
                                putExtra(Intent.EXTRA_TEXT, "https://picsum.photos/id/${currentBackStackEntry?.arguments?.getString("wallpaperId")}/1080/1920")
                            }
                            context.startActivity(Intent.createChooser(intent, "Share via"))
                        }
                    } else null
                )
            },
        ) { innerPadding ->
            // navigation logic
            NavHost(
                navController = navController,
                startDestination = Screens.HomeScreen.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(Screens.HomeScreen.name) {
                    LaunchedEffect(Unit) {
                        if(viewModel.wallpapers.isEmpty()) {
                            viewModel.loadWallpapers()
                        }
                    }
                    if(viewModel.wallpapers.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text("Loading...")
                        }
                    } else {
                        HomeScreen(navController = navController, wallpapers = viewModel.wallpapers)
                    }
                }

                composable(Screens.FavoritesScreen.name) {
                    LaunchedEffect(viewModel.favoritesNeedRefresh) {
                        if(viewModel.favoritesNeedRefresh) {
                            viewModel.favoritesNeedRefresh = false
                            Log.d("letest", "ran")
                            viewModel.loadFavorites()
                        }
                    }

                    if(viewModel.favoriteWallpapers.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text("Add some favorites to get started!\n\nIf you have already added favorites, they will show soon...")
                        }
                    } else {
                        FavoritesScreen(navController = navController, wallpapers = viewModel.favoriteWallpapers)
                    }
                }

                // go to appropriate DetailScreen based on route
                composable("${Screens.DetailScreen.name}/{wallpaperId}") { navRequest ->
                    val wallpaperId = navRequest.arguments?.getString("wallpaperId")
                    var wallpaper = viewModel.wallpapers.find { it.id == wallpaperId }
                        ?: viewModel.favoriteWallpapers.find { it.id == wallpaperId }

                    if (wallpaper != null) {
                        DetailScreen(viewModel = viewModel, wallpaper = wallpaper)
                    } else {
                        Text("This text should not appear...Contact us at gearturnerdev@gmail.com to report this bug!")
                    }
                }

                composable(Screens.SettingsScreen.name) {
                    SettingsScreen(toggleAccentColor)
                }

                composable(Screens.InfoScreen.name) {
                    InfoScreen()
                }
            }
        }
    }

    // hamburger menu content, only shown if on HomeScreen
    if(currentScreen != Screens.DetailScreen) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(top = 54.dp, end = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(280.dp)
                            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
                            .background(
                                if (currentSelection == "Home") Color(0xFFCFFFF9) else Color.Transparent
                            )
                            .clickable {
                                currentSelection = "Home"
                                navController.navigate(Screens.HomeScreen.name)
                                scope.launch { drawerState.close() }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Home",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(280.dp)
                            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
                            .background(
                                if (currentSelection == "Favs") Color(0xFFCFFFF9) else Color.Transparent
                            )
                            .clickable {
                                currentSelection = "Favs"
                                navController.navigate(Screens.FavoritesScreen.name)
                                scope.launch { drawerState.close() }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Favorites",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(280.dp)
                            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
                            .background(
                                if (currentSelection == "Settings") Color(0xFFCFFFF9) else Color.Transparent
                            )
                            .clickable {
                                currentSelection = "Settings"
                                navController.navigate(Screens.SettingsScreen.name)
                                scope.launch { drawerState.close() }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Settings",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(280.dp)
                            .clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
                            .background(
                                if (currentSelection == "Info") Color(0xFFCFFFF9) else Color.Transparent
                            )
                            .clickable {
                                currentSelection = "Info"
                                navController.navigate(Screens.InfoScreen.name)
                                scope.launch { drawerState.close() }
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Info",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        ) {
            scaffoldBody()
        }
    } else {
        scaffoldBody()
    }
}