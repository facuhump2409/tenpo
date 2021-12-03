package com.example.tenpo.services

import org.springframework.stereotype.Service

@Service
class MultiplierService {
    fun multiply(a: Int, b: Int): Int {
        return a * b
    }
}