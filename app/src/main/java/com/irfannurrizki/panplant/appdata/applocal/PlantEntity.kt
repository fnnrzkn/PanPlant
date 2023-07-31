package com.irfannurrizki.panplant.appdata.applocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant")
data class PlantEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val description: String,
    val photoUrl: String,
    val categories:String,
    var isFavorite: Boolean = false
)

