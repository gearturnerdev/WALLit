/*
authors: Hunter Pageau and MD Fayed bin Salim
version: 1 May 2025
enum class to define all navigation destinations/screens in the app
 */

package dev.gearturner.wallit.navigation

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