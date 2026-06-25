package com.example.managemotel.domain.model

data class Tenant(
    val tenantId: String,
    val fullName: String,
    val phone: String,
    val idCard: String,
    val email: String,
    val roomId: String?
)
