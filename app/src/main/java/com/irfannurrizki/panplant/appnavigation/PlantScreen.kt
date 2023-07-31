package com.irfannurrizki.panplant.appnavigation

sealed class PlantScreen (val route: String) {
    object Home : PlantScreen("home")
    object Favorite : PlantScreen("favorite")
    object Profile : PlantScreen("profile")
    object Detail : PlantScreen("home/{plantId}") {
        fun createRoute(plantId: Int) = "home/$plantId"
    }
}