package com.example.tenpo.repos

import com.example.tenpo.model.requests.Request
import org.springframework.stereotype.Repository
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Repository
class RequestsRepo {
    val requests: MutableList<Request> = mutableListOf()

    fun saveRequest(request: HttpServletRequest, response: HttpServletResponse) {
        val newRequest = Request(request.requestURI, request.method, response.status)
        requests.add(newRequest)
    }
}