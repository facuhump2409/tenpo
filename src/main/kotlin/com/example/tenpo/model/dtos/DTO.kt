package com.example.tenpo.model.dtos

import java.util.*

sealed class DTO {
    data class ExceptionDTO(val message: String?) : DTO()
    data class UserDto(
        val id: UUID,
        val mail: String
    ) : DTO()
}
