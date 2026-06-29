package com.example.managemotel.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoomCard(
    roomId: String,
    price: Double,
    typeRooms: String,
    status: String,
    paymentStatus: String?,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "Room $roomId", style = MaterialTheme.typography.titleMedium)
            Text(text = "Price: $price")
            Text(text = "Type: $typeRooms")
            Text(text = "Status: $status")
            Text(text = "Payment: ${paymentStatus ?: "N/A"}")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RoomCard() {

    RoomCard(
        roomId = "101",
        price = 2500000.0,
        typeRooms = "Thường",
        status = "OCCUPIED",
        paymentStatus = "Đã đóng"
    )
}