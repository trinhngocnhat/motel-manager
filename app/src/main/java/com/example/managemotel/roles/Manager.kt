package com.example.managemotel.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.managemotel.components.*
import com.example.managemotel.ui.theme.*

@Composable
fun ManagerScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "QUẢN LÝ", navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall)) }

            item { CommonLabelsRow(labels = listOf("Hôm nay", "Sự cố mới", "Yêu cầu", "Tiến độ")) }

            // Công việc cần xử lý
            item {
                SectionTitle(title = "Công việc hôm nay")
                Row(
                    modifier = Modifier.fillMaxWidth(), 
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                ) {
                    MetricCard(
                        label = "Ghi điện nước", 
                        value = "05 Phòng", 
                        icon = Icons.Default.FlashOn, 
                        color = AppCardBlue, 
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("manager_tasks") }
                    )
                    MetricCard(
                        label = "Sự cố mới", 
                        value = "02 Yêu cầu", 
                        icon = Icons.Default.Build, 
                        color = AppCardRed, 
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                SectionTitle(title = "Tiến độ công việc", onActionClick = {})
                VisualizeBox(title = "Danh sách chi tiết tiến độ (List View)", height = 240)
            }

            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge)) }
        }
    }
}

@Composable
fun TaskListScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "DANH SÁCH CÔNG VIỆC", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            SectionTitle(title = "Ghi chỉ số điện nước (Tháng 5)")
            
            MotelTable(
                headers = listOf("Phòng", "Chỉ số cũ", "Chỉ số mới", "Trạng thái"),
                rows = listOf(
                    listOf("P.101", "1250", "1380", "Hoàn thành"),
                    listOf("P.102", "890", "945", "Hoàn thành"),
                    listOf("P.201", "2100", "-", "Chưa ghi"),
                    listOf("P.202", "-", "-", "Phòng trống"),
                    listOf("P.301", "1540", "-", "Chưa ghi")
                )
            )
            
            MetricCard(
                label = "Phòng cần ghi hôm nay", 
                value = "05 Phòng", 
                icon = Icons.Default.FlashOn, 
                color = AppCardBlue,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
