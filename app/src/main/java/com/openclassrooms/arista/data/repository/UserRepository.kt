package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.UserDao
import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.data.UserDto
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

class UserRepository(
    //private val apiService: FakeApiService = FakeApiService()
    private val userDao : UserDao
) {

//    // Get the current user
//    var user: User
//        get() = apiService.user
//        // Set or update the user
//        set(user) {
//            apiService.user = user
//        }

    suspend fun getUserById(id : Long) : User? {

       val userDto = userDao.getUserById(id).first()
        if (userDto!=null){
            return User.fromDto(userDto)
        }
        return null
//        if (usersDto.size > 0){
//            return User.fromDto(usersDto.get(0))
//        }
//       return null


    }

}