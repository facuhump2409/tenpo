package com.example.tenpo.controllers.filters

import com.example.tenpo.repos.RequestsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


@Component
class RequestResponseFilter(@Autowired private val requestRepo: RequestsRepo) : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        println("Persisting request")
        val req = request as HttpServletRequest
        chain?.doFilter(request, response)
        println("Logging Request ")
        println(req.method)
        println(req.requestURI)
        requestRepo.saveRequest(req)
    }
}