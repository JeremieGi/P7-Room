package com.openclassrooms.arista.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.SleepDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SleepDao {
    @Insert
    suspend fun insertSleep(sleep: SleepDto): Long

    @Query("SELECT * FROM sleep")
    fun getAllSleeps(): Flow<List<SleepDto>>

    @Query("SELECT * FROM sleep WHERE userId = :userId")
    fun getSleepsOfUser(userId : Long): Flow<List<SleepDto>>

    @Query("DELETE FROM sleep WHERE id = :id")
    suspend fun deleteSleepById(id: Long)


}