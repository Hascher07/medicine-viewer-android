package com.app.medicineviewer.domain.interactor

import com.app.medicineviewer.domain.GetMedicinesUseCase
import javax.inject.Inject

class MedicineInteractor @Inject constructor(
    val getMedicinesUseCase: GetMedicinesUseCase
)
