package com.example.tenpo.model.users

import com.example.tenpo.model.Requestable
import com.example.tenpo.model.dtos.DTO
import java.util.*

class User(
    val id: UUID,
    val email: String,
    val password: String,
    val salt: String
) : Requestable {
    override fun dto(): DTO.UserDto {
        return DTO.UserDto(
            id,
            email
        )
    }

}