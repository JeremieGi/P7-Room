package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.ExerciseDao
import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first

class ExerciseRepository(
    private val exerciseDao : ExerciseDao
) {

    // Get all exercises
    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
            .first() // Collect the first emission of the Flow
            .map {
                Exercise.fromDto(it)  // Convert every DTO in Exercise // it est de type ExerciseDto ici
            }

    }

    // Add a new exercise
    suspend fun addExercise(exercise: Exercise, idUser : Long) {
        //apiService.addExercise(exercise)
        exerciseDao.insertExercise(exercise.toDto(idUser))
    }


    // Delete an exercise
    suspend fun deleteExercise(exercise: Exercise) {

        // If there is no id, you can raise an exception and catch it in the use case and viewmodel
        exercise.id?.let {
            exerciseDao.deleteExerciseById(
                id = exercise.id,
            )
        }
    }
}