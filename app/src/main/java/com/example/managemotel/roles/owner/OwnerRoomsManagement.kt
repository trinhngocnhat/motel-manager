package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.components.MotelTable
import com.example.managemotel.components.SectionTitle
import com.example.managemotel.ui.theme.AppDimensions
import com.example.managemotel.viewmodels.OwnerViewModel

@Composable
fun OwnerRoomsManagementScreen(
    navController: NavController,
    viewModel: OwnerViewModel = viewModel()
) {
    val rooms by viewModel.allRooms.collectAsState()

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "QUẢN LÝ PHÒNG TRỌ",
                navController = navController,
                showBackButton = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            SectionTitle(title = "Danh sách phòng trọ")

            val headers = listOf("Phòng", "Loại", "Tầng", "Trạng thái")
            val rows = rooms.map { room ->
                listOf(room.roomId, room.typeRooms, room.floor.toString(), room.status)
            }

            if (rows.isNotEmpty()) {
                MotelTable(headers = headers, rows = rows)
            } else {
                Text("Chưa có phòng nào.")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Tính năng cập nhật loại phòng và gán người thuê sẽ được thực hiện qua các dialog chi tiết (Mockup logic đã sẵn sàng trong ViewModel).", 
                 style = MaterialTheme.typography.bodySmall)
        }
    }
}
