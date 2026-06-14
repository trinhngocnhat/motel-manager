package com.example.managemotel.roles.manager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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

            // Công việc cần xử lý
            item {
                SectionTitle(title = "Công việc hôm nay")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                ) {

                    MetricCard( // onClick to navigate to the Room Management CRUD page
                        label = "Phòng trọ",
                        value = "05 Phòng",
                        icon = Icons.Default.FlashOn,
                        color = AppCardBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("room_management") }
                    )

                    MetricCard( // onClick to navigate to the Issue Management CRUD page
                        label = "Sự cố mới",
                        value = "02 Yêu cầu",
                        icon = Icons.Default.Build,
                        color = AppCardRed,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("issues_management") }
                    )

                    MetricCard( // onClick to navigate to the Tenant Management CRUD page
                        label = "Khách thuê",
                        value = "02 Yêu cầu",
                        icon = Icons.Default.Build,
                        color = AppCardRed,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("tenant_management") }
                    )

                    MetricCard( // onClick to navigate to the Revenue Management page
                        label = "Doanh thu",
                        value = "02 Yêu cầu",
                        icon = Icons.Default.Build,
                        color = AppCardRed,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("revenue_management") }
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

@Preview(showBackground = true)
@Composable
fun ManagerScreenPreview() {
    val navController = rememberNavController()
    ManagerScreen(navController = navController)
}

