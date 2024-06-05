package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import javax.inject.Inject

// Les useCase ne font que passe plat ici

class GetUserUsecase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(id : Long): Result<User> {
        return userRepository.getUserById(id)
    }

}