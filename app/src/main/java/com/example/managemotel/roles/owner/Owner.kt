package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.*
import com.example.managemotel.ui.theme.*

@Composable
fun OwnerScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "CHỦ NHÀ", navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall)) }

            // Nhãn lọc nhanh
            item { CommonLabelsRow(labels = listOf("Tất cả", "Khu A", "Khu B", "Khu C", "Báo cáo")) }

            // Chỉ số quan trọng
            item {
                SectionTitle(title = "Tổng quan hôm nay")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
                ) {

                    MetricCard(
                        label = "Phòng trọ",
                        value = "15 Phòng",
                        icon = Icons.Default.FlashOn,
                        color = AppCardBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("owner_room_management") }
                    )

                    MetricCard(
                        label = "Sự cố",
                        value = "01 Yêu cầu",
                        icon = Icons.Default.Build,
                        color = AppCardRed,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("owner_issues_management") }
                    )

                    MetricCard(
                        label = "Khách thuê",
                        value = "12 Khách",
                        icon = Icons.Default.Person,
                        color = AppCardOrange,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("owner_tenant_management") }
                    )

                    MetricCard(
                        label = "Doanh thu",
                        value = "150M",
                        icon = Icons.Default.AttachMoney,
                        color = AppCardGreen,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("owner_revenue_management") }
                    )
                }
            }

            // Biểu đồ & Thống kê
            item {
                SectionTitle(title = "Phân tích doanh thu", onActionClick = { navController.navigate("owner_revenue") })
                VisualizeBox(title = "Doanh thu theo tháng (Bar Chart)", height = 200)
            }

            item {
                SectionTitle(title = "Tỷ lệ lấp đầy", onActionClick = {})
                VisualizeBox(title = "Tỷ lệ lấp đầy phòng (Pie Chart)", height = 180)
            }

            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerScreenPreview() {
    val navController = rememberNavController()
    OwnerScreen(navController = navController)
}

@Composable
fun RevenueReportScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "BÁO CÁO DOANH THU", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            SectionTitle(title = "Chi tiết doanh thu tháng 5")
            
            MotelTable(
                headers = listOf("Phòng", "Tiền nhà", "Điện/Nước", "Khác", "Tổng"),
                rows = listOf(
                    listOf("P.101", "3.5M", "450K", "50K", "4.0M"),
                    listOf("P.102", "3.5M", "320K", "50K", "3.87M"),
                    listOf("P.201", "4.0M", "600K", "100K", "4.7M"),
                    listOf("P.202", "Trống", "-", "-", "-"),
                    listOf("P.301", "4.0M", "550K", "50K", "4.6M")
                )
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppCardGreen.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(label = "Tổng doanh thu dự tính", value = "21.0M")
                    DetailRow(label = "Đã thu", value = "17.17M")
                    DetailRow(label = "Còn nợ", value = "3.83M", isTotal = true)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RevenueReportScreenPreview() {
    val navController = rememberNavController()
    RevenueReportScreen(navController = navController)
}
