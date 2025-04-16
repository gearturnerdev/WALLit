package dev.gearturner.wallit.navigation

enum class Screens {
    HomeScreen,
    FavoritesScreen,
    DetailScreen,
    SettingsScreen,
    InfoScreen;
    // handle routes to make navigation easier
    companion object {
        fun fromRoute(route: String?): Screens
                = when(route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FavoritesScreen.name -> FavoritesScreen
            DetailScreen.name -> DetailScreen
            SettingsScreen.name -> SettingsScreen
            InfoScreen.name -> InfoScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}