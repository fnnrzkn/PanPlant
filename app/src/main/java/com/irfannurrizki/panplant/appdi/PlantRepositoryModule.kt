package com.irfannurrizki.panplant.appdi

import com.irfannurrizki.panplant.appdata.PlantRepository
import com.irfannurrizki.panplant.appdata.applocal.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PlantRepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideRepository(plantDao: PlantDao) = PlantRepository(plantDao)
}