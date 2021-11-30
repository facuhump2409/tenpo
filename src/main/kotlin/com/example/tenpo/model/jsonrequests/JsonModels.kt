package com.example.tenpo.model.jsonrequests

import com.fasterxml.jackson.annotation.JsonCreator

data class UserForm @JsonCreator constructor(
    val mail: String,
    val password: String
)