package com.irfannurrizki.panplant.appui.screen.planthome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfannurrizki.panplant.PlantUIState
import com.irfannurrizki.panplant.appdata.PlantRepository
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appmodel.PlantsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantHomeViewModel @Inject constructor(private val plantRepository: PlantRepository) : ViewModel() {
    private val _allPlants = MutableStateFlow<PlantUIState<List<PlantEntity>>>(PlantUIState.Loading)
    val allPlants = _allPlants.asStateFlow()

    private val _plantHomeState = mutableStateOf(PlantHomeState())
    val plantHomeState: State<PlantHomeState> = _plantHomeState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.getAllPlants().collect { plant ->
                when (plant.isEmpty()) {
                    true -> plantRepository.insertAllPlants(PlantsData.dummyplants)
                    else -> _allPlants.value = PlantUIState.Success(plant)
                }
            }
        }
    }

    private fun searchPlant(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.searchPlant(query)
                .catch { _allPlants.value = PlantUIState.Error(it.message.toString()) }
                .collect { _allPlants.value = PlantUIState.Success(it) }
        }
    }

    fun updateFavoritePlant(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.updateFavoritePlant(id, isFavorite)
        }
    }

    fun onQueryChange(query: String) {
        _plantHomeState.value = _plantHomeState.value.copy(query = query)
        searchPlant(query)
    }
}