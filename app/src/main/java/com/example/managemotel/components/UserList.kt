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
fun UserList(
    users: List<User>,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users, key = { it.id }) { user ->
            UserCard(user = user, onClick = { onUserClick(user) })
        }
    }
}

@Composable
private fun UserCard(user: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Role: ${user.role}", modifier = Modifier.padding(top = 4.dp), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
