package dev.gearturner.wallit.navigation

//Enum class to define all navigation destinations/screens in the app
enum class Screens {
    HomeScreen,
    FavoritesScreen,
    DetailScreen,
    SettingsScreen,
    InfoScreen;
    //Companion object provides utility methods related to Screens
    companion object {

        //Converts a route string into a corresponding Screens enum value
        fun fromRoute(route: String?): Screens
                = when(route?.substringBefore("/")) {  //Handles routes
            HomeScreen.name -> HomeScreen
            FavoritesScreen.name -> FavoritesScreen
            DetailScreen.name -> DetailScreen
            SettingsScreen.name -> SettingsScreen
            InfoScreen.name -> InfoScreen
            null -> HomeScreen  //Default to HomeScreen if route is null
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}