package com.irfannurrizki.panplant.appui.screen.plantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfannurrizki.panplant.PlantUIState
import com.irfannurrizki.panplant.appdata.PlantRepository
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantDetailViewModel @Inject constructor(private val plantRepository: PlantRepository) : ViewModel() {
    private val _plant = MutableStateFlow<PlantUIState<PlantEntity>>(PlantUIState.Loading)
    val plant = _plant.asStateFlow()

    fun getPlant(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.getPlant(id)
                .catch { _plant.value = PlantUIState.Error(it.message.toString()) }
                .collect { _plant.value = PlantUIState.Success(it) }
        }
    }

    fun updateFavoritePlant(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.updateFavoritePlant(id, isFavorite)
        }
    }
}