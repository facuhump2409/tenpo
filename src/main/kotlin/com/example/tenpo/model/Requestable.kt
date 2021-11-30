package com.example.tenpo.model

import com.example.tenpo.model.dtos.DTO

interface Requestable {
    fun dto(): DTO
}