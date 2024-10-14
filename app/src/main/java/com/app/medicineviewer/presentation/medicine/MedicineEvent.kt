package com.app.medicineviewer.presentation.medicine

sealed class MedicineEvent {
    data object FetchMedicines : MedicineEvent()
    data class InitializeMedicineDetails(val medicineId: String) : MedicineEvent()
}
