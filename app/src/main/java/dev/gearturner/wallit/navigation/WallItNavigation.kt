package dev.gearturner.wallit.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onMenuClick: (() -> Unit)? = null,
    modifier: Modifier,
    onShare: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary
        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        actions = { // share button
            if(onShare != null) {
                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        tint = MaterialTheme.colorScheme.onSecondary,
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
                        tint = MaterialTheme.colorScheme.onSecondary,
                        contentDescription = null
                    )
                }
            } else if(onMenuClick != null) { // hamburger menu otherwise
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        tint = MaterialTheme.colorScheme.onSecondary,
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
    val currentScreen = Screens.fromRoute(currentBackStackEntry?.destination?.route)
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

    // Scaffold that surrounds the screens, contains TopAppBar
    val scaffoldBody = @Composable {
        Scaffold(
            topBar = {
                NavBar(
                    title = title,
                    canNavigateBack = canNavigateBack,
                    navigateUp = { navController.navigate(Screens.HomeScreen.name) },
                    modifier = Modifier,
                    onMenuClick = { scope.launch { drawerState.open() } },

                    // share logic
                    onShare = if(currentScreen == Screens.DetailScreen) {
                        {
                            // TODO: share logic
                        }
                    } else null
                )
            }
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
                        Text("Loading...")
                    } else {
                        HomeScreen(navController = navController, wallpapers = viewModel.wallpapers)
                    }
                }

                composable(Screens.FavoritesScreen.name) {
                    LaunchedEffect(Unit) {
                        if(viewModel.sampleWallpapers.isEmpty()) {
                            viewModel.loadSampleWallpapers()
                        }
                    }
                    if(viewModel.sampleWallpapers.isEmpty()) {
                        Text("Loading...")
                    } else {
                        FavoritesScreen(navController = navController, wallpapers = viewModel.sampleWallpapers)
                    }
                }

                // go to appropriate DetailScreen based on route
                composable("${Screens.DetailScreen.name}/{wallpaperId}") { navRequest ->
                    val wallpaperId = navRequest.arguments?.getString("wallpaperId")
                    var wallpaper = viewModel.wallpapers.find { it.id == wallpaperId }
                        ?: viewModel.sampleWallpapers.find { it.id == wallpaperId }

                    if (wallpaper != null) {
                        DetailScreen(navController = navController, wallpaper = wallpaper)
                    } else {
                        Text("This text should not appear...Contact us at gearturnerdev@gmail.com to report this bug!")
                    }
                }

                composable(Screens.SettingsScreen.name) {
                    SettingsScreen()
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
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 54.dp, end = 8.dp)
                ) {
                    Text(
                        text = "Home",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.navigate(Screens.HomeScreen.name)
                                scope.launch { drawerState.close() }
                            }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Favorites",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.navigate(Screens.FavoritesScreen.name)
                                scope.launch { drawerState.close() }
                            }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Settings",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.navigate(Screens.SettingsScreen.name)
                                scope.launch { drawerState.close() }
                            }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Info",
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.navigate(Screens.InfoScreen.name)
                                scope.launch { drawerState.close() }
                            }
                    )
                }
            }
        ) {
            scaffoldBody()
        }
    } else {
        scaffoldBody()
    }
}