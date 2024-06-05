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
    suspend fun getUserById(id : Long) : User? {

        val flow = userDao.getUserById(id)
        val user = flow.first()
        if (user!=null){
            return User.fromDto(user)
        }
        return null // Cas où la base de données est vide


    }

}