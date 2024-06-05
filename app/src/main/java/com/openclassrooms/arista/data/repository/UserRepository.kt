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
        val userDto = flow.first()
        if (userDto!=null){ // TODO : Ici warning : Condition 'userDto!=null' is always 'true' mais çà arrive avec une base de données pas encore créée
            return User.fromDto(userDto)
        }
        return null // Cas où la base de données n'est pas créée


    }

}