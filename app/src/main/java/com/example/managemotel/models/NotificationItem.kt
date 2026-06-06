package com.example.managemotel.models

data class NotificationItem(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: String,
    val isRead: Boolean
)
