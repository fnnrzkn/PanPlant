package com.irfannurrizki.panplant

sealed class PlantUIState<out T: Any?> {
    object Loading : PlantUIState<Nothing>()
    data class Success<out T: Any>(val data: T) : PlantUIState<T>()
    data class Error(val message: String) : PlantUIState<Nothing>()
}
