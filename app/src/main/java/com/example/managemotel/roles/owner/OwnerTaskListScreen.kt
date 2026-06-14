package com.example.managemotel.roles.owner

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
fun OwnerTaskListScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "CHỦ NHÀ - CÔNG VIỆC", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            SectionTitle(title = "Theo dõi tiến độ công việc")

            MotelTable(
                headers = listOf("Công việc", "Người phụ trách", "Tiến độ", "Trạng thái"),
                rows = listOf(
                    listOf("Ghi điện nước", "Manager A", "80%", "Đang thực hiện"),
                    listOf("Bảo trì P.105", "Kỹ thuật B", "100%", "Hoàn thành"),
                    listOf("Thu tiền phòng", "Manager A", "50%", "Đang thực hiện")
                )
            )

            MetricCard(
                label = "Công việc tồn đọng",
                value = "03 Mục",
                icon = Icons.Default.FlashOn,
                color = AppCardBlue,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerTaskListScreenPreview() {
    val navController = rememberNavController()
    OwnerTaskListScreen(navController = navController)
}
