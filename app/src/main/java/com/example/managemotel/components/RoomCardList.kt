package com.example.managemotel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.managemotel.models.MotelRoom

@Composable
fun MotelRoomCardList(
    rooms: List<MotelRoom>,
    modifier: Modifier = Modifier,
    onRoomClick: (MotelRoom) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(rooms, key = { it.roomId }) { room ->
            RoomCard(
                roomId = room.roomId,
                price = room.price,
                typeRooms = room.typeRooms,
                status = room.status,
                paymentStatus = room.paymentStatus,
                onClick = { onRoomClick(room) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRoomCardList() {
    val sampleRooms = listOf(
        MotelRoom(
            roomId = "101",
            price = 2500000.0,
            typeRooms = "Thường",
            status = "OCCUPIED",
            floor = 1,
            note = "",
            managerId = "M001",
            paymentStatus = "Đã đóng"
        ),
        MotelRoom(
            roomId = "102",
            price = 3000000.0,
            typeRooms = "VIP",
            status = "AVAILABLE",
            floor = 1,
            note = "",
            managerId = "M001",
            paymentStatus = "Chưa đóng"
        )
    )
    MotelRoomCardList(rooms = sampleRooms)
}
