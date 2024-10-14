package com.app.medicineviewer.domain.repository

import com.app.medicineviewer.data.Medicine
import com.app.medicineviewer.data.MedicineDao
import com.app.medicineviewer.domain.MedicineRepo
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val repo: MedicineRepo,
    private val medicineDao: MedicineDao
) {
    suspend fun getMedicinesFromApi(): List<Medicine> {
        return try {
            val response = repo.getMedicines()
            if (response.isSuccessful && response.body() != null) {
                val medicines = response.body()?.medicines?.map { medicineResponse ->
                    Medicine(
                        id = medicineResponse.id,
                        name = medicineResponse.name,
                        dose = medicineResponse.dose,
                        strength = medicineResponse.strength,
                        imageUrl = medicineResponse.imageUrl,
                        details = medicineResponse.details
                    )
                } ?: emptyList()

                medicineDao.insertMedicines(medicines)
                medicines
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getMedicinesFromRoom(): List<Medicine> {
        return medicineDao.getMedicines()
    }
}



