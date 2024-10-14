package com.app.medicineviewer.presentation.medicine

import com.app.medicineviewer.data.Medicine

data class MedicineDataState(
    var medicines: List<Medicine> = emptyList(),
    var selectedMedicine: Medicine? = null
)
