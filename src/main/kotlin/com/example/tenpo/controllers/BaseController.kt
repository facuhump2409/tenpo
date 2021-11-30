package com.example.tenpo.controllers

import arrow.core.getOrHandle
import com.example.tenpo.controllers.helpers.JwtSigner
import com.example.tenpo.exceptions.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.util.WebUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

abstract class BaseController {

    @ExceptionHandler(CustomException.NotFound::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(exception: CustomException) = exception.dto()

    @ExceptionHandler(CustomException.Forbidden::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenException(exception: CustomException) = exception.dto()

    @ExceptionHandler(CustomException.BadRequest::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(exception: CustomException) = exception.dto()

    @ExceptionHandler(CustomException.Unauthorized::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(exception: CustomException) = exception.dto()

    fun checkAndGetUserId(request: HttpServletRequest): String {
        val cookie: Cookie? = WebUtils.getCookie(request, "X-Auth")
        val jwt = cookie?.value
        return JwtSigner.validateJwt(jwt).map { token -> token.body.subject }
            .getOrHandle { throw CustomException.Unauthorized.TokenException() }
    }
}