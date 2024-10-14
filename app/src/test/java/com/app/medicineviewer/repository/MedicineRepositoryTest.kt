package com.app.medicineviewer.repository

import com.app.medicineviewer.data.MedicineDao
import com.app.medicineviewer.domain.MedicineRepo
import com.app.medicineviewer.domain.repository.MedicineRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.junit.Assert.assertTrue

class MedicineRepositoryTest {

    @Mock
    private lateinit var medicineRepo: MedicineRepo
    @Mock
    private lateinit var medicineDao: MedicineDao
    private lateinit var medicineRepository: MedicineRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        medicineRepository = MedicineRepository(medicineRepo, medicineDao)
    }

    @Test
    fun `getMedicinesFromApi function should return empty list on API failure`() = runBlocking {
        `when`(medicineRepo.getMedicines()).thenThrow(RuntimeException("Network error"))
        val result = medicineRepository.getMedicinesFromApi()
        assertTrue(result.isEmpty())
    }
}
