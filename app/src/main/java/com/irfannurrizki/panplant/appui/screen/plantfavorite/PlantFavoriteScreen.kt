package com.irfannurrizki.panplant.appui.screen.plantfavorite

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.irfannurrizki.panplant.PlantMainScreen
import com.irfannurrizki.panplant.PlantUIState
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appui.components.PlantContentEmpty
import com.irfannurrizki.panplant.appui.components.PlantContentError
import com.irfannurrizki.panplant.appui.components.PlantContentItem
import com.irfannurrizki.panplant.appui.components.PlantLoadingIndicator
import com.irfannurrizki.panplant.ui.theme.PanPlantTheme

@Composable
fun PlantFavoriteScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val plantFavoriteViewModel = hiltViewModel<PlantFavoriteViewModel>()

    plantFavoriteViewModel.allFavoritePlants.collectAsState(PlantUIState.Loading).value.let { plantUIState ->
        when (plantUIState) {
            is PlantUIState.Loading -> PlantLoadingIndicator()
            is PlantUIState.Error -> PlantContentError()
            is PlantUIState.Success -> {
                PlantFavoriteContent(
                    listFavoritePlant = plantUIState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateFavoritePlant = plantFavoriteViewModel::updateFavoritePlant
                )
            }
        }
    }
}

@Composable
fun PlantFavoriteContent(
    listFavoritePlant: List<PlantEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlant: (id: Int, isFavorite: Boolean) -> Unit
) {
    when (listFavoritePlant.isEmpty()) {
        true -> PlantContentEmpty()
        false -> PlantContentItem(listFavoritePlant, navController, scaffoldState, onUpdateFavoritePlant)
    }
}
