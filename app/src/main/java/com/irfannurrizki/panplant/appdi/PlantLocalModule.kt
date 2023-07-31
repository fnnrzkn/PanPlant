package com.irfannurrizki.panplant.appdi

import android.app.Application
import androidx.room.Room
import com.irfannurrizki.panplant.appdata.applocal.PlantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantLocalModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, PlantDatabase::class.java, "panplant.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: PlantDatabase) = database.plantDao()
}