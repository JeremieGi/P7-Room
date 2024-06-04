package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.SleepDao
import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.first

class SleepRepository(
    private val sleepDAO: SleepDao
) {

    // Get all sleep records
    suspend fun allSleeps(): List<Sleep> {
        return sleepDAO.getAllSleeps()
            .first() // Collect the first emission of the Flow
            .map {
                Sleep.fromDto(it) // Convert every DTO in Sleep // it est de type SleepDto ici
            }

    }
}