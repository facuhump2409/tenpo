package com.example.tenpo.services

import arrow.core.extensions.option.foldable.isNotEmpty
import arrow.core.getOrElse
import com.example.tenpo.controllers.helpers.JwtSigner
import com.example.tenpo.exceptions.CustomException
import com.example.tenpo.model.dtos.DTO
import com.example.tenpo.model.jsonrequests.UserForm
import com.example.tenpo.model.users.User
import com.example.tenpo.model.utils.SHA512Hash
import com.example.tenpo.repos.UsersRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.WebUtils
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletRequest


@Service
class UserControllerService(
    @Autowired private val usersRepo: UsersRepo
) {
    companion object {
        private const val TOKEN_HEADER = "X-Auth"
    }

    fun createUser(newUser: UserForm): DTO.UserDto {
        if (usersRepo.findByMail(newUser.mail).isNotEmpty())
            throw CustomException.BadRequest.RepeatedUserException("There is already a user under that email")
        val (salt, hashedPassword) = SHA512Hash.hashPassword(newUser.password)
        val user = User(UUID.randomUUID(), newUser.mail, hashedPassword, salt)
        usersRepo.saveUser(user)
        return user.dto()
    }

    fun checkUserCredentials(user: UserForm): User {
        return usersRepo.findUserByMailAndPassword(user.mail, user.password)
            .getOrElse { throw CustomException.Unauthorized.BadLoginException() }
    }

    fun login(user: User): ResponseEntity<DTO.UserDto> {
        val jwt = JwtSigner.createJwt(user.email)
        val authCookie = ResponseCookie.fromClientResponse(TOKEN_HEADER, jwt)
            .maxAge(3600)
            .httpOnly(true)
            .path("/")
            .secure(false)
            .build()
        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.id)
            .toUri()
        return ResponseEntity.created(location).header("Set-Cookie", authCookie.toString()).body(user.dto())
    }

    fun logout(request: HttpServletRequest): ResponseEntity<Void> {
        val authCookie =
            ResponseCookie.fromClientResponse(TOKEN_HEADER, WebUtils.getCookie(request, TOKEN_HEADER)!!.value)
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .secure(false)
                .build()
        return ResponseEntity.ok().header("Set-Cookie", authCookie.toString()).build()
    }
}