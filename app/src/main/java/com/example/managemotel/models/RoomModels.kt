package com.example.managemotel.models

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "rooms")
data class Room(
    @PrimaryKey
    val id: String,
    val status: RoomStatus,
    val tenantName: String? = null,
    val phoneNumber: String? = null,
    val lastElectricity: Int = 0,
    val lastWater: Int = 0,
    val paymentStatus: String = "Chưa đóng",
    val rentalType: String = "Dài hạn",
    val furnishing: String = "Có vật tư",
    val electricityPrice: Int = 3500,
    val waterPrice: Int = 20000,
    val deposit: Long = 3000000,
    val rentalStartDate: String = "01/01/2024",
    val rentalEndDate: String = "01/06/2025",
    val furnitureList: List<String> = emptyList()
)

enum class RoomStatus(val label: String, val colorValue: Int) {
    AVAILABLE("Phòng trống", 0xFF4CAF50.toInt()),
    OCCUPIED("Đang ở", 0xFFF44336.toInt()),
    MAINTENANCE("Bảo trì", 0xFF9E9E9E.toInt());

    val color: Color get() = Color(colorValue)
}
