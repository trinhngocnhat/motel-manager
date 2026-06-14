package com.example.managemotel.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        items(rooms, key = { it.id }) { room ->
            MotelRoomCard(room = room, onClick = { onRoomClick(room) })
        }
    }
}

@Composable
private fun MotelRoomCard(room: MotelRoom, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Room ${room.number}", style = MaterialTheme.typography.titleLarge)
            }
            Text(text = "Status: ${room.status}", modifier = Modifier.padding(top = 4.dp), style = MaterialTheme.typography.bodyMedium)
            Text(text = "Price: $${room.price}", modifier = Modifier.padding(top = 2.dp), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
