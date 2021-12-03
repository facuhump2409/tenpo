package com.example.tenpo.controllers

import com.example.tenpo.model.dtos.DTO
import com.example.tenpo.services.MultiplierService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RequestMapping("/multiplication")
@RestController
class MultiplierController(
    @Autowired private val multiplierService: MultiplierService,
) : BaseController() {
    @GetMapping //It could also be done with a body, but it seems much easier this way
    fun createUser(
        @RequestParam number: Int,
        @RequestParam anotherNumber: Int,
        request: HttpServletRequest
    ): ResponseEntity<DTO.Result> {
        checkUserLogin(request)
        val result = multiplierService.multiply(number, anotherNumber)
        return ResponseEntity.ok(DTO.Result(result))
    }
}