package com.example.managemotel.domain.model

import com.example.managemotel.domain.enums.RoomStatus
import com.example.managemotel.domain.enums.RoomType

data class Room(
    val roomId: String,
    val roomNumber: String,
    val type: RoomType,
    val status: RoomStatus,
    val price: Double,
    val description: String?
)
