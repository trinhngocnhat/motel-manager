package com.example.managemotel.domain.model

data class Invoice(
    val invoiceId: String,
    val roomId: String,
    val amount: Double,
    val issueDate: String,
    val dueDate: String,
    val status: String // PAID, UNPAID
)
