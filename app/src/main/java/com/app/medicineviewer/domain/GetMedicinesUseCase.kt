package com.app.medicineviewer.domain

import com.app.medicineviewer.domain.repository.MedicineRepository
import com.app.medicineviewer.core.domain.ErrorType
import com.app.medicineviewer.core.domain.NetworkExceptionHandling
import com.app.medicineviewer.core.domain.ProgressBarState
import com.app.medicineviewer.core.domain.Resource
import com.app.medicineviewer.core.utils.UIComponent
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor(
    private val repo: MedicineRepository,
    private val errorHandling: NetworkExceptionHandling
) {
    operator fun invoke() = flow {
        emit(Resource.Loading(ProgressBarState.Loading))
        try {
            val result = repo.getMedicinesFromApi()
            if (result.isNotEmpty()) emit(Resource.Success(result))
            else emit(Resource.Error(UIComponent.None("No medicines found"), ErrorType.UNKNOWN))
        } catch (e: Exception) {
            emit(errorHandling.execute(e))
        } finally {
            emit(Resource.Loading(ProgressBarState.Idle))
        }
    }
}
