package com.example.tenpo.controllers

import com.example.tenpo.model.dtos.DTO
import com.example.tenpo.model.jsonrequests.UserForm
import com.example.tenpo.services.UserControllerService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RequestMapping("/users")
@RestController
class UserController(
    @Autowired var userControllerService: UserControllerService,
) : BaseController() {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping
    fun createUser(@RequestBody newUser: UserForm): ResponseEntity<DTO.UserDto> {
        userControllerService.createUser(newUser)
        return ResponseEntity.created(URI.create("/users")).build()
    }

    @PostMapping("/tokens")
    fun login(@RequestBody _user: UserForm): ResponseEntity<DTO.UserDto> {
        val userDto = userControllerService.checkUserCredentials(_user)
        return userControllerService.login(userDto)
    }

    @DeleteMapping("/tokens")
    fun logout(request: HttpServletRequest): ResponseEntity<Void> {
        logger.info("Logging out user")
        checkUserLogin(request)
        return userControllerService.logout(request)
    }
}