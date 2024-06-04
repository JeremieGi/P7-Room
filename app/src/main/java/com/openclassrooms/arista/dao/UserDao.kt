package com.openclassrooms.arista.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.SleepDto
import com.openclassrooms.arista.data.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserDto): Long

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): Flow<UserDto>


}