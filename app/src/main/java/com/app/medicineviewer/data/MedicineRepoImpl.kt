package com.app.medicineviewer.data

import com.app.medicineviewer.domain.MedicineRepo
import com.app.medicineviewer.response.getmedicines.GetMedicinesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MedicineRepoImpl @Inject constructor(private val api: MedicineApi) : MedicineRepo {

    override suspend fun getMedicines(): Response<GetMedicinesResponse> = withContext(
        Dispatchers.IO) {
        api.getMedicines()
    }
}
