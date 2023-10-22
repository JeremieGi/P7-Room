package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    fun execute(exercise: Exercise) {
        exerciseRepository.deleteExercise(exercise)
    }
}