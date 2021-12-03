package com.example.tenpo.controllers

import com.example.tenpo.model.dtos.DTO
import com.example.tenpo.services.RequestsServiceController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RequestMapping("/requests")
@RestController
class RequestsController(@Autowired private val requestsServiceController: RequestsServiceController) :
    BaseController() {
    @GetMapping
    fun getRequests(request: HttpServletRequest, @RequestParam page: Int): DTO.RequestsDto {
        checkUserLogin(request)
        return requestsServiceController.getRequestsHistoricalData(page)
    }
}