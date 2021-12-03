package com.example.tenpo.repos

import com.example.tenpo.model.requests.Request
import org.springframework.data.jpa.repository.JpaRepository

interface RequestsRepo : JpaRepository<Request, Long> {
}