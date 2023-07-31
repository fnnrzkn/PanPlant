package com.irfannurrizki.panplant.appui.screen.plantfavorite

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
class PlantFavoriteViewModel @Inject constructor(private val plantRepository: PlantRepository) : ViewModel() {
    private val _allFavoritePlants = MutableStateFlow<PlantUIState<List<PlantEntity>>>(PlantUIState.Loading)
    val allFavoritePlants = _allFavoritePlants.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.getAllFavoritePlants()
                .catch { _allFavoritePlants.value = PlantUIState.Error(it.message.toString()) }
                .collect { _allFavoritePlants.value = PlantUIState.Success(it) }
        }
    }

    fun updateFavoritePlant(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.updateFavoritePlant(id, isFavorite)
        }
    }
}