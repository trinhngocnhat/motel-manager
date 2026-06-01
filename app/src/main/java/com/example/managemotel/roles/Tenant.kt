package com.example.managemotel.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.managemotel.components.*
import com.example.managemotel.ui.theme.*

@Composable
fun TenantScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "NGƯỜI THUÊ", navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall)) }

            item { CommonLabelsRow(labels = listOf("Hóa đơn", "Dịch vụ", "Hợp đồng", "Phản hồi")) }

            // Tình trạng thanh toán
            item {
                SectionTitle(title = "Thanh toán tháng này")
                Row(
                    modifier = Modifier.fillMaxWidth(), 
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                ) {
                    MetricCard(
                        label = "Tổng tiền", 
                        value = "3.2M", 
                        icon = Icons.Default.ReceiptLong, 
                        color = Color(0xFF007ACC), 
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("tenant_bill") }
                    )
                    MetricCard(
                        label = "Trạng thái", 
                        value = "Đã thu", 
                        icon = Icons.Default.CheckCircle, 
                        color = AppCardGreen, 
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Chỉ số sử dụng
            item {
                SectionTitle(title = "Chỉ số dịch vụ")
                Row(
                    modifier = Modifier.fillMaxWidth(), 
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                ) {
                    MetricCard(
                        label = "Số điện", 
                        value = "120 kWh", 
                        icon = Icons.Default.Bolt, 
                        color = Color(0xFFFFB300), 
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        label = "Số nước", 
                        value = "8 m³", 
                        icon = Icons.Default.WaterDrop, 
                        color = Color(0xFF039BE5), 
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                SectionTitle(title = "Biểu đồ tiêu thụ", onActionClick = {})
                VisualizeBox(title = "Thống kê chỉ số hàng tháng (Line Chart)", height = 200)
            }

            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge)) }
        }
    }
}

@Composable
fun BillDetailScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "CHI TIẾT HÓA ĐƠN", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            SectionTitle(title = "Hóa đơn tháng 05/2024")
            
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(label = "Tiền phòng", value = "3.000.000 đ")
                    DetailRow(label = "Tiền điện (120 kWh x 3k)", value = "360.000 đ")
                    DetailRow(label = "Tiền nước (8 m3 x 10k)", value = "80.000 đ")
                    DetailRow(label = "Dịch vụ khác", value = "50.000 đ")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    DetailRow(label = "TỔNG THANH TOÁN", value = "3.490.000 đ", isTotal = true)
                }
            }
            
            Button(
                onClick = { /* Thanh toán action */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(AppDimensions.RadiusLarge)
            ) {
                Text("THANH TOÁN NGAY", fontWeight = FontWeight.Bold)
            }
            
            Text(
                text = "* Vui lòng thanh toán trước ngày 10 hàng tháng để tránh phát sinh phí trễ.",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
