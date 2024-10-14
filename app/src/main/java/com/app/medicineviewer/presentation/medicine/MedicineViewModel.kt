package com.app.medicineviewer.presentation.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.medicineviewer.domain.interactor.MedicineInteractor
import com.app.medicineviewer.domain.repository.MedicineRepository
import com.app.medicineviewer.core.domain.ErrorType
import com.app.medicineviewer.core.domain.Resource
import com.app.medicineviewer.core.utils.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val medicineInteractor: MedicineInteractor,
    private val medicineRepository: MedicineRepository
) : ViewModel() {

    private val _medicineDataState = MutableStateFlow(MedicineDataState())
    val medicineDataState: StateFlow<MedicineDataState> = _medicineDataState

    private val _medicineUiState = MutableStateFlow<MedicineUiState>(MedicineUiState.Nothing)
    val medicineUiState: StateFlow<MedicineUiState> = _medicineUiState

    fun onEvent(event: MedicineEvent) {
        when (event) {
            is MedicineEvent.FetchMedicines -> fetchMedicines()
            is MedicineEvent.InitializeMedicineDetails -> initializeMedicineDetails(event.medicineId)
            else -> {}
        }
    }

    private fun fetchMedicines() {
        viewModelScope.launch {
            medicineInteractor.getMedicinesUseCase.invoke().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _medicineDataState.value = _medicineDataState.value.copy(medicines = result.data ?: emptyList())
                        _medicineUiState.value =
                            MedicineUiState.MedicinesFetched
                    }
                    is Resource.Error ->
                        _medicineUiState.value =
                            MedicineUiState.Error(result.uiComponent, result.type)
                    is Resource.Loading ->
                        _medicineUiState.value = MedicineUiState.Loading(result.progressBarState)
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun initializeMedicineDetails(medicineId: String) {
        viewModelScope.launch {
            val medicineDetails = medicineRepository.getMedicinesFromRoom().find { it.id == medicineId.toIntOrNull() }
            if (medicineDetails != null) {
                _medicineDataState.value = _medicineDataState.value.copy(selectedMedicine = medicineDetails)
                _medicineUiState.value = MedicineUiState.MedicineDetailsInitialized
            } else {
                _medicineUiState.value = MedicineUiState.Error(
                    UIComponent.Dialog("Medicine not found", "Please try again"),
                    ErrorType.UNKNOWN
                )
            }
        }
    }
}



