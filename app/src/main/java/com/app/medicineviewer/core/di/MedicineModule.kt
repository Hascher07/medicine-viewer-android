package com.app.medicineviewer.core.di

import android.content.Context
import androidx.room.Room
import com.app.medicineviewer.domain.GetMedicinesUseCase
import com.app.medicineviewer.core.utils.Logger
import com.app.medicineviewer.data.MedicineApi
import com.app.medicineviewer.data.MedicineDao
import com.app.medicineviewer.domain.interactor.MedicineInteractor
import com.app.medicineviewer.data.MedicineRepoImpl
import com.app.medicineviewer.domain.repository.MedicineRepository
import com.app.medicineviewer.core.domain.NetworkExceptionHandling
import com.app.medicineviewer.core.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MedicineModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "medicine_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMedicineDao(appDatabase: AppDatabase): MedicineDao {
        return appDatabase.medicineDao()
    }

    @Provides
    @Singleton
    fun provideMedicineRepository(
        medicineApi: MedicineApi,
        medicineDao: MedicineDao
    ): MedicineRepository {
        return MedicineRepository(MedicineRepoImpl(medicineApi), medicineDao)
    }

    @Provides
    @Singleton
    fun provideGetMedicinesUseCase(repository: MedicineRepository): GetMedicinesUseCase {
        return GetMedicinesUseCase(repository, NetworkExceptionHandling(Logger("")))
    }

    @Provides
    @Singleton
    fun provideMedicineInteractor(getMedicinesUseCase: GetMedicinesUseCase): MedicineInteractor {
        return MedicineInteractor(getMedicinesUseCase)
    }
}

