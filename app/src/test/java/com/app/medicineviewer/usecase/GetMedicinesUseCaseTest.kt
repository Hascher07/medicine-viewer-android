package com.app.medicineviewer.usecase

import com.app.medicineviewer.core.domain.NetworkExceptionHandling
import com.app.medicineviewer.core.domain.Resource
import com.app.medicineviewer.core.utils.Logger
import com.app.medicineviewer.data.Medicine
import com.app.medicineviewer.domain.GetMedicinesUseCase
import com.app.medicineviewer.domain.repository.MedicineRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class GetMedicinesUseCaseTest {

    @Mock
    private lateinit var medicineRepository: MedicineRepository
    private lateinit var getMedicinesUseCase: GetMedicinesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val errorHandling = NetworkExceptionHandling(Logger(""))
        getMedicinesUseCase = GetMedicinesUseCase(medicineRepository, errorHandling)
    }

    @Test
    fun `invoke method should return Resource Success when medicines are fetched successfully`() = runTest {
        val medicines = listOf(
            Medicine(id = 2, name = "Ibuprofen", dose = "200 mg", strength = "Mild",
                details = "Use to treat pain", imageUrl = "https://www.dvago.pk/_next/image?url=https%3A%2F%2Fdvago-assets.s3.ap-southeast-1.amazonaws.com%2FProductsImages%2F06389%2520(1).jpg&w=1920&q=50")
        )
        flow { emit(Resource.Success(medicines)) }
        `when`(medicineRepository.getMedicinesFromApi()).thenReturn(medicines)

        val results = getMedicinesUseCase.invoke().toList()
        assertTrue(results.isNotEmpty())
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Success)
        assertTrue((results[1] as Resource.Success).data == medicines)
    }
}
