package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import javax.inject.Inject

class GetAllSleepsUseCase @Inject constructor(private val sleepRepository: SleepRepository) {
//    fun execute(): List<Sleep> {
//        return sleepRepository.allSleeps
//    }

    suspend fun execute(): List<Sleep> {
        return sleepRepository.allSleeps()
    }
}