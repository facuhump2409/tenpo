package com.example.tenpo.model.dtos

import com.example.tenpo.model.requests.Request
import java.util.*

sealed class DTO {
    data class ExceptionDTO(val message: String?) : DTO()
    data class UserDto(
        val id: UUID,
        val mail: String
    ) : DTO()

    data class Result(val result: Int) : DTO()

    data class RequestsDto(val requests: List<Request>, val page: Int, val nextPage: String?) : DTO()
}
