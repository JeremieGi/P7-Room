package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.UserDto


data class User(
    var name: String,
    var email: String
){

    companion object {
        fun fromDto(userDto: UserDto) : User {

            return User(
                name = userDto.sName,
                email = userDto.sEmail
            )

        }
    }
}