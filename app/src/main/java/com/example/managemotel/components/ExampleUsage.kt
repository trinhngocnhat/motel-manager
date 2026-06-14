package com.example.managemotel.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun ExampleUsagePreview() {
    ExampleUsage()
}

@Composable
fun ExampleUsage() {
    val (query, setQuery) = remember { mutableStateOf("") }

    // Sample data
    val rooms = listOf(
        MotelRoom(1, "101", "Available", 45.0),
        MotelRoom(2, "102", "Occupied", 55.0),
        MotelRoom(3, "103", "Cleaning", 50.0)
    )

    Column {
        SearchBar(query = query, onQueryChange = setQuery, onSearch = { /* perform search */ })
        MotelRoomCardList(rooms = rooms, onRoomClick = { /* handle room click */ })
    }
}
