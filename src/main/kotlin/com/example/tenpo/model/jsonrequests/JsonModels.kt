package com.example.tenpo.model.jsonrequests

import com.fasterxml.jackson.annotation.JsonCreator
import java.math.BigDecimal

data class UserForm @JsonCreator constructor(
    val mail: String,
    val password: String
)