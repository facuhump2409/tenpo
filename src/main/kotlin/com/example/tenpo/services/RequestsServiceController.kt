package com.example.tenpo.services

import com.example.tenpo.exceptions.CustomException
import com.example.tenpo.model.dtos.DTO
import com.example.tenpo.repos.RequestsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RequestsServiceController(
    @Autowired private val requestsRepo: RequestsRepo,
    @Value("\${requests.per.page:2}") private val requestsPerPage: Int
) {
    fun getRequestsHistoricalData(page: Int): DTO.RequestsDto {
        val requests = requestsRepo.requests
        val requestsCount = requests.size
        val pages = calculatePages(requestsCount)
        if (pages < page || page < 1)
            throw CustomException.BadRequest.PageDoesNotExistException("Page $page does not exist")
        val from = if(page == 1) 0 else (page - 1) * requestsPerPage
        var to = (page) * requestsPerPage
        if(page == pages) to = requestsCount
        if (page == 1) to = requestsPerPage
        val sublist = requests.subList(from, to)
        val nextPage: String? = if (page < pages) "/requests?page=${page + 1}" else null
        return DTO.RequestsDto(sublist, page, nextPage)
    }

    fun calculatePages(size: Int): Int {
        return (size / requestsPerPage) + if (size % requestsPerPage == 0) 0 else 1
    }
}