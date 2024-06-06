package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.UserDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

class UserRepository(
    //private val apiService: FakeApiService = FakeApiService()
    private val userDao : UserDao
) {

    /**
     * Renvoie l'utilisateur à partir de son id
     */
    suspend fun getUserById(id : Long) : Result<User> {

        return try {
            val userDto = userDao.getUserById(id).first()
            // Ici userDto peut-être null si la base de données n'a pas été créée (Si c'est le cas, l'exception sera catchée)
            val userModel = userDto.toModelUser()
            Result.success(userModel)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}