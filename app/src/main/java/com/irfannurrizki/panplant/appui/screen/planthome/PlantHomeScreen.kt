package com.irfannurrizki.panplant.appui.screen.planthome

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.irfannurrizki.panplant.PlantUIState
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appui.components.*
import com.irfannurrizki.panplant.ui.theme.PanPlantTheme


@Composable
fun PlantHomeScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val plantHomeViewModel = hiltViewModel<PlantHomeViewModel>()
    val plantHomeState by plantHomeViewModel.plantHomeState

    plantHomeViewModel.allPlants.collectAsState(PlantUIState.Loading).value.let { plantUIState ->
        when (plantUIState) {
            is PlantUIState.Loading -> PlantLoadingIndicator()
            is PlantUIState.Error -> PlantContentError()
            is PlantUIState.Success -> {
                PlantHomeContent(
                    listPlant = plantUIState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    query = plantHomeState.query,
                    onQueryChange = plantHomeViewModel::onQueryChange,
                    onUpdateFavoritePlant = plantHomeViewModel::updateFavoritePlant
                )
            }
        }
    }
}

@Composable
fun PlantHomeContent(
    listPlant: List<PlantEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    query: String,
    onQueryChange: (String) -> Unit,
    onUpdateFavoritePlant: (id: Int, isFavorite: Boolean) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        when (listPlant.isEmpty()) {
            true -> PlantContentEmpty()
            false -> PlantContentItem(listPlant, navController, scaffoldState, onUpdateFavoritePlant)
        }
    }
}



