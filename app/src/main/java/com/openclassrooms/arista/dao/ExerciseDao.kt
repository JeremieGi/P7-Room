package com.openclassrooms.arista.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.ExerciseDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: ExerciseDto): Long

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<ExerciseDto>>

    @Query("SELECT * FROM exercise  WHERE userId = :idUser")
    fun getExercisesOfUser(idUser : Long): Flow<List<ExerciseDto>>

    @Query("DELETE FROM exercise WHERE id = :id")
    suspend fun deleteExerciseById(id: Long)


}