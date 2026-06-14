package com.example.managemotel.roles.manager

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.components.MetricCard
import com.example.managemotel.components.MotelTable
import com.example.managemotel.components.SectionTitle
import com.example.managemotel.ui.theme.AppCardBlue
import com.example.managemotel.ui.theme.AppDimensions

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

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    val navController = rememberNavController()
    TaskListScreen(navController = navController)
}
