package com.example.managemotel.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.managemotel.models.MotelRoom

@Preview(showBackground = true)
@Composable
fun ExampleUsagePreview() {
    ExampleUsage()
}

@Composable
fun ExampleUsage() {
    val (query, setQuery) = remember { mutableStateOf("") }

    // Sample data using the models.MotelRoom entity structure
    val rooms = listOf(
        MotelRoom("101", "RT01", "AVAILABLE", 1, "Near entrance"),
        MotelRoom("102", "RT01", "OCCUPIED", 1, null),
        MotelRoom("103", "RT02", "MAINTENANCE", 1, null)
    )

    Column {
        SearchBar(query = query, onQueryChange = setQuery, onSearch = { /* perform search */ })
        MotelRoomCardList(rooms = rooms, onRoomClick = { /* handle room click */ })
    }
}
