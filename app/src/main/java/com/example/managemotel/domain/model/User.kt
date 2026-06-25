package com.example.managemotel.domain.model

import com.example.managemotel.domain.enums.UserRole

data class User(
    val userId: String,
    val username: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val role: UserRole
)
