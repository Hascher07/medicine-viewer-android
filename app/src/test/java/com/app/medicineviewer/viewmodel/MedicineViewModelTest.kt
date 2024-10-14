package com.app.medicineviewer.viewmodel

import com.app.medicineviewer.core.domain.Resource
import com.app.medicineviewer.data.Medicine
import com.app.medicineviewer.domain.GetMedicinesUseCase
import com.app.medicineviewer.domain.interactor.MedicineInteractor
import com.app.medicineviewer.domain.repository.MedicineRepository
import com.app.medicineviewer.presentation.medicine.MedicineEvent
import com.app.medicineviewer.presentation.medicine.MedicineUiState
import com.app.medicineviewer.presentation.medicine.MedicineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MedicineViewModelTest {

    private lateinit var getMedicinesUseCase: GetMedicinesUseCase
    private lateinit var medicineInteractor: MedicineInteractor
    private val mockMedicineRepository: MedicineRepository = mock()
    private lateinit var viewModel: MedicineViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        getMedicinesUseCase = mock()
        medicineInteractor = MedicineInteractor(getMedicinesUseCase)
        viewModel = MedicineViewModel(medicineInteractor, mockMedicineRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMedicines should update UI state to MedicinesFetched when success`() = runTest {
        val mockMedicines = listOf(
            Medicine(
                id = 1,
                name = "Paracetamol",
                dose = "200 mg",
                strength = "Strong",
                details = "Use to treat pain",
                imageUrl = "https://phabcart.imgix.net/cdn/scdn/images/uploads/m0459_web.jpg?auto=compress&lossless=1&w=385"
            ),
            Medicine(
                id = 2,
                name = "Ibuprofen",
                dose = "200 mg",
                strength = "Mild",
                details = "Use to treat pain",
                imageUrl = "https://www.dvago.pk/_next/image?url=https%3A%2F%2Fdvago-assets.s3.ap-southeast-1.amazonaws.com%2FProductsImages%2F06389%2520(1).jpg&w=1920&q=50"
            )
        )
        val successFlow = flowOf(Resource.Success(mockMedicines))
        `when`(getMedicinesUseCase.invoke()).thenReturn(successFlow)
        viewModel.onEvent(MedicineEvent.FetchMedicines)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.medicineUiState.value
        assertTrue(state is MedicineUiState.MedicinesFetched)
    }
}
