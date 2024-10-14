package com.app.medicineviewer.presentation.medicine

import com.app.medicineviewer.core.domain.ErrorType
import com.app.medicineviewer.core.domain.ProgressBarState
import com.app.medicineviewer.core.utils.UIComponent

sealed class MedicineUiState {
    data object MedicinesFetched : MedicineUiState()
    data object MedicineDetailsInitialized : MedicineUiState()
    data class Loading(val progressBarState: ProgressBarState = ProgressBarState.Idle) : MedicineUiState()
    data class Error(val uiComponent: UIComponent, val type: ErrorType) : MedicineUiState()
    data object Nothing : MedicineUiState()
}
