package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.ExerciseDao
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first

class ExerciseRepository(
    private val exerciseDao : ExerciseDao
) {

//    // Get all exercises
//    suspend fun getAllExercises(): List<Exercise> {
//        return exerciseDao.getAllExercises()
//            .first() // Collect the first emission of the Flow
//            .map {
//                Exercise.fromDto(it)  // Convert every DTO in Exercise // it est de type ExerciseDto ici
//            }
//    }

    // Get all exercises of one user
    suspend fun getExercisesOfUser(idUser : Long): List<Exercise> {
        return exerciseDao.getExercisesOfUser(idUser)
            .first() // Collect the first emission of the Flow
            .map {
                //Exercise.fromDto(it)  // Convert every DTO in Exercise // it est de type ExerciseDto ici
                it.toModelExercice()
            }
    }

    // Add a new exercise
    suspend fun addExercise(exercise: Exercise, idUser : Long) {

        // TODO : Ici j'utilise directement les Exceptions (pas la classe Result)
        try{
            exerciseDao.insertExercise(exercise.toDto(idUser))
        }
        catch (e: Exception){
            // Cette exception n'a pas de valeur ajoutée réelle mais on pourrait gérer nos propres classes d'exception ici
            throw Exception("Exception lors du insert exercice : ${e.localizedMessage}")
        }

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