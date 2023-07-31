package com.irfannurrizki.panplant

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appnavigation.PlantNavigationItem
import com.irfannurrizki.panplant.appnavigation.PlantScreen
import com.irfannurrizki.panplant.appui.screen.plantdetail.PlantDetailScreen
import com.irfannurrizki.panplant.appui.screen.plantfavorite.PlantFavoriteScreen
import com.irfannurrizki.panplant.appui.screen.planthome.PlantHomeContent
import com.irfannurrizki.panplant.appui.screen.planthome.PlantHomeScreen
import com.irfannurrizki.panplant.appui.screen.plantprofile.PlantProfileScreen
import com.irfannurrizki.panplant.ui.theme.PanPlantTheme

@Composable
fun PlantMainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != PlantScreen.Detail.route) {
                BottomBar(navController, currentRoute)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, shape = RoundedCornerShape(10.dp))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PlantScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(PlantScreen.Home.route) {
                PlantHomeScreen(navController, scaffoldState)
            }
            composable(
                route = PlantScreen.Detail.route,
                arguments = listOf(
                    navArgument("plantId") { type = NavType.IntType }
                )
            ) {
                val plantId = it.arguments?.getInt("plantId") ?: 0
                PlantDetailScreen(plantId, navController, scaffoldState)
            }
            composable(PlantScreen.Favorite.route) {
                PlantFavoriteScreen(navController, scaffoldState)
            }
            composable(PlantScreen.Profile.route) {
                PlantProfileScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        PlantNavigationItem(
            title = "Home",
            icon = Icons.Rounded.Home,
            plantScreen = PlantScreen.Home
        ),
        PlantNavigationItem(
            title = "Favorite",
            icon = Icons.Rounded.Favorite,
            plantScreen = PlantScreen.Favorite
        ),
        PlantNavigationItem(
            title = "about_page",
            icon = Icons.Rounded.Person,
            plantScreen = PlantScreen.Profile
        ),
    )

    BottomNavigation(backgroundColor = Color.Magenta) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.plantScreen.route,
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = Color.Yellow,
                onClick = {
                    navController.navigate(item.plantScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PlantHomeScreenPreview() {
    PanPlantTheme{
        PlantMainScreen(
        )
    }
}