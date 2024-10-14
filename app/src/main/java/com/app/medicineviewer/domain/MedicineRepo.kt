package com.app.medicineviewer.domain

import com.app.medicineviewer.response.getmedicines.GetMedicinesResponse
import retrofit2.Response

interface MedicineRepo {
    suspend fun getMedicines(): Response<GetMedicinesResponse>
}