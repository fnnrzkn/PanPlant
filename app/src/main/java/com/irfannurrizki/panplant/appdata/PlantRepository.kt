package com.irfannurrizki.panplant.appdata

import com.irfannurrizki.panplant.appdata.applocal.PlantDao
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appmodel.Plant
import com.irfannurrizki.panplant.appmodel.PlantsData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao) {
    fun getAllPlants() = plantDao.getAllPlants()
    fun getAllFavoritePlants() = plantDao.getAllFavoritePlants()
    fun getPlant(id: Int) = plantDao.getPlant(id)
    fun searchPlant(query: String) = plantDao.searchPlant(query)
    suspend fun insertAllPlants(plant: List<PlantEntity>) = plantDao.insertAllPlants(plant)
    suspend fun updateFavoritePlant(id: Int, isFavorite: Boolean) = plantDao.updateFavoritePlants(id, isFavorite)
}
