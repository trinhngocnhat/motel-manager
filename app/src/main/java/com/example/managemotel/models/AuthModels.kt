package com.example.managemotel.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val user: UserDto,
    val token: String
)
