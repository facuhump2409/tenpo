package com.example.tenpo.repos

import com.example.tenpo.model.requests.Request
import org.springframework.stereotype.Repository
import javax.servlet.http.HttpServletRequest

@Repository
class RequestsRepo {
    val requests: MutableList<Request> = mutableListOf()

    fun saveRequest(request: HttpServletRequest) {
        val newRequest = Request(request.requestURI, request.method)
        requests.add(newRequest)
    }
}