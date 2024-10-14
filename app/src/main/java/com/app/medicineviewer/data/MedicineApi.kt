package com.app.medicineviewer.data

import com.app.medicineviewer.response.getmedicines.GetMedicinesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MedicineApi {
    @GET("v3/7376643d-c02e-4489-8ff0-82eccb01535a")
    suspend fun getMedicines(): Response<GetMedicinesResponse>
}


