package com.example.tenpo.exceptions

import com.example.tenpo.model.dtos.DTO

sealed class CustomException(message: String) : Exception(message) {
    sealed class NotFound(message: String) : CustomException("Not found: $message") {
    }

    sealed class Unauthorized(message: String) : CustomException("Unauthorized: $message") {
        class TokenException(message: String = "Token expired. Login Again") : Unauthorized(message)
        class BadLoginException : Unauthorized("wrong username or password")
    }

    sealed class BadRequest(message: String) : CustomException("Bad request: $message") {
        class RepeatedUserException(message: String) : BadRequest(message)
        class NoMatchingUserTypeException :
            BadRequest("No user type found for the one provided. Please try again with types USER or FIXER")
    }

    sealed class Forbidden(message: String?) : CustomException("Forbidden: $message") {
    }

    sealed class InternalServer(message: String) : CustomException("Internal server error: $message") {
    }

    fun dto(): DTO.ExceptionDTO = DTO.ExceptionDTO(message) // Default

}
typealias TokenException = CustomException.Unauthorized.TokenException
