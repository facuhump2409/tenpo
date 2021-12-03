package com.example.tenpo.services

import com.example.tenpo.exceptions.CustomException
import com.example.tenpo.model.requests.Request
import com.example.tenpo.repos.RequestsRepo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RequestsServiceControllerTest {
    private lateinit var requestsServiceController: RequestsServiceController
    private lateinit var requestsServiceControllerWith3PerPage: RequestsServiceController

    @MockK
    private lateinit var requestsRepo: RequestsRepo

    @MockK
    private lateinit var request: Request

    @MockK
    private lateinit var anotherRequest: Request

    @MockK
    private lateinit var yetAnotherRequest: Request

    @MockK
    private lateinit var ohGodAnotherRequest: Request

    @MockK
    private lateinit var ohGodYetAnotherRequest: Request
    private val amountPerPage = 2

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        requestsServiceController = RequestsServiceController(requestsRepo, amountPerPage)
        requestsServiceControllerWith3PerPage = RequestsServiceController(requestsRepo, 3)
        every { requestsRepo.findAll() } returns listOf(
            request,
            anotherRequest,
            yetAnotherRequest,
            ohGodAnotherRequest,
            ohGodYetAnotherRequest
        )
    }

    @Test
    fun `getRequestsHistoricalData with 2 pages per page, should return 3 pages one with 1 page`() {
        var requestDto = requestsServiceController.getRequestsHistoricalData(1)

        assertEquals(2, requestDto.requests.size)
        assertEquals("/requests?page=2", requestDto.nextPage)

        requestDto = requestsServiceController.getRequestsHistoricalData(2)

        assertEquals(2, requestDto.requests.size)
        assertEquals("/requests?page=3", requestDto.nextPage)

        requestDto = requestsServiceController.getRequestsHistoricalData(3)

        assertEquals(1, requestDto.requests.size)
        assertNull(requestDto.nextPage)
    }

    @Test
    fun `getRequestsHistoricalData with 3 pages per page, should return 2 pages one error if out of range`() {
        var requestDto = requestsServiceControllerWith3PerPage.getRequestsHistoricalData(1)

        assertEquals(3, requestDto.requests.size)
        assertEquals("/requests?page=2", requestDto.nextPage)

        requestDto = requestsServiceControllerWith3PerPage.getRequestsHistoricalData(2)

        assertEquals(2, requestDto.requests.size)
        assertNull(requestDto.nextPage)

        assertThrows<CustomException.BadRequest.PageDoesNotExistException> {
            requestsServiceControllerWith3PerPage.getRequestsHistoricalData(
                3
            )
        }
    }
}