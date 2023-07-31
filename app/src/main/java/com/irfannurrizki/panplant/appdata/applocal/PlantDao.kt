package com.irfannurrizki.panplant.appdata.applocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plant WHERE isFavorite = 1")
    fun getAllFavoritePlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plant WHERE id = :id")
    fun getPlant(id: Int): Flow<PlantEntity>

    @Query("SELECT * FROM plant WHERE name LIKE '%' || :query || '%'")
    fun searchPlant(query: String): Flow<List<PlantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlants(plantList: List<PlantEntity>)

    @Query("UPDATE plant SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoritePlants(id: Int, isFavorite: Boolean)
}