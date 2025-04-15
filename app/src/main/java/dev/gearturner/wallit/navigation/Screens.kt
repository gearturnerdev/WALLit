package dev.gearturner.wallit.navigation

enum class Screens {
    HomeScreen,
    FavoritesScreen,
    DetailScreen,
    SettingsScreen,
    HelpScreen;

    // handle routes to make navigation easier
    companion object {
        fun fromRoute(route: String?): Screens
                = when(route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FavoritesScreen.name -> FavoritesScreen
            DetailScreen.name -> DetailScreen
            SettingsScreen.name -> SettingsScreen
            HelpScreen.name -> HelpScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}