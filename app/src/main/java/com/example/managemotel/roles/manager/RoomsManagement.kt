package com.example.managemotel.roles.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.models.RoomWithTenant
import com.example.managemotel.ui.theme.AppDimensions
import com.example.managemotel.ui.theme.AppPrimaryBlue
import com.example.managemotel.viewmodels.RoomViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun RoomsManagementScreen(
    navController: NavController,
    viewModel: RoomViewModel = viewModel()
) {
    val rooms by viewModel.allRoomsWithTenants.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.syncRoomsFromBackend()
    }

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "QUẢN LÝ PHÒNG TRỌ",
                navController = navController,
                showBackButton = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.syncRoomsFromBackend() },
                containerColor = AppPrimaryBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Sync")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Header Table
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppPrimaryBlue.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Phòng / Loại", modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("Giá thuê", modifier = Modifier.weight(1.2f), fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.End)
                Text("Trạng thái", modifier = Modifier.weight(1.3f), fontWeight = FontWeight.Bold, fontSize = 12.sp, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (rooms.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Chưa có danh sách phòng", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(rooms) { room ->
                        RoomItemCard(room)
                    }
                }
            }
        }
    }
}

@Composable
fun RoomItemCard(room: RoomWithTenant) {
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click to show details */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1.5f)) {
                    Text(
                        text = "Phòng ${room.roomId}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = AppPrimaryBlue
                    )
                    Text(
                        text = room.typeRooms,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = currencyFormatter.format(room.price),
                    modifier = Modifier.weight(1.2f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.End,
                    color = Color(0xFFE91E63)
                )

                Box(modifier = Modifier.weight(1.3f), contentAlignment = Alignment.CenterEnd) {
                    StatusBadge(room.status)
                }
            }

            if (room.status == "OCCUPIED") {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = Color.LightGray)
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        LabelText("Người thuê")
                        ValueText(room.tenantName ?: "N/A")
                        if (room.tenantId != null) {
                            Text("ID: ${room.tenantId}", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        LabelText("Quản lý")
                        ValueText(room.managerName ?: "N/A")
                        if (room.managerId != null) {
                            Text("ID: ${room.managerId}", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (text, color) = when (status) {
        "AVAILABLE" -> "Trống" to Color(0xFF4CAF50)
        "OCCUPIED" -> "Đã thuê" to Color(0xFFF44336)
        "MAINTENANCE" -> "Bảo trì" to Color(0xFFFF9800)
        else -> status to Color.Gray
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(6.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LabelText(text: String) {
    Text(text = text, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
}

@Composable
fun ValueText(text: String) {
    Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
}
