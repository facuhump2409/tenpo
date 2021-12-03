package com.example.tenpo.services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MultiplierServiceTest {
    @Test
    fun multiply() {
        val multiplierService = MultiplierService()
        assertEquals(10, multiplierService.multiply(2, 5))
    }
}