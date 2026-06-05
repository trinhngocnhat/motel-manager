package com.example.managemotel.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.*
import com.example.managemotel.ui.theme.*
import java.util.Locale

data class RevenueItem(
    val roomId: String,
    val tenantName: String,
    val amount: Long,
    val date: String,
    val status: String // "Đã thanh toán" / "Chưa thanh toán"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueManagementScreen(navController: NavController) {
    // SQLITE: Dữ liệu này sẽ được tính toán từ bảng hóa đơn (Bills) trong database
    val totalRevenue = 45000000L
    val collectedAmount = 38500000L
    val pendingAmount = totalRevenue - collectedAmount

    val recentTransactions = listOf(
        RevenueItem("101", "Nguyễn Văn A", 3750000L, "15/06/2024", "Đã thanh toán"),
        RevenueItem("102", "Trần Thị B", 3500000L, "14/06/2024", "Đã thanh toán"),
        RevenueItem("201", "Lê Văn C", 4200000L, "---", "Chưa thanh toán"),
        RevenueItem("305", "Phạm Văn D", 3800000L, "12/06/2024", "Đã thanh toán")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("QUẢN LÝ DOANH THU", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppPrimaryBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tổng kết tháng
            item {
                SectionTitle(title = "Tổng kết Tháng 06/2024")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricCard(
                        label = "Tổng thu",
                        value = formatCurrency(totalRevenue),
                        icon = Icons.Default.AccountBalance,
                        color = AppCardBlue,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        label = "Đã thu",
                        value = formatCurrency(collectedAmount),
                        icon = Icons.Default.CheckCircle,
                        color = AppCardGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                MetricCard(
                    label = "Còn nợ (Chưa thu)",
                    value = formatCurrency(pendingAmount),
                    icon = Icons.Default.Error,
                    color = AppCardOrange,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Biểu đồ/Trực quan hóa
            item {
                SectionTitle(title = "Xu hướng doanh thu")
                VisualizeBox(title = "Biểu đồ tăng trưởng 6 tháng", height = 180)
            }

            // Danh sách giao dịch gần đây
            item {
                SectionTitle(title = "Giao dịch gần đây")
            }

            items(recentTransactions) { item ->
                TransactionCard(item)
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun TransactionCard(item: RevenueItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Phòng ${item.roomId} - ${item.tenantName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = if (item.status == "Đã thanh toán") "Ngày thu: ${item.date}" else "Chưa thanh toán",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formatCurrency(item.amount),
                    fontWeight = FontWeight.Bold,
                    color = AppPrimaryBlue,
                    fontSize = 16.sp
                )
                Text(
                    text = item.status,
                    color = if (item.status == "Đã thanh toán") Color(0xFF2E7D32) else Color(0xFFC62828),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun formatCurrency(amount: Long): String {
    return String.format(Locale.US, "%,dđ", amount)
}

@Preview(showBackground = true)
@Composable
fun RevenueManagementPreview() {
    RevenueManagementScreen(rememberNavController())
}
