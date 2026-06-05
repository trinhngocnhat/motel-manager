package com.example.managemotel.roles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.managemotel.components.*
import com.example.managemotel.ui.theme.*

@Composable
fun OwnerScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("Tất cả") }

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
            item { 
                CommonLabelsRow(
                    labels = listOf("Tất cả", "Q.1", "Q.7", "Báo cáo"),
                    selectedLabel = selectedFilter,
                    onLabelClick = { selectedFilter = it }
                ) 
            }

            // Các chỉ số quan trọng
            item {
                SectionTitle(title = "Tổng quan $selectedFilter")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
                ) {
                    val revenue = if(selectedFilter == "Q.1") "85M" else if(selectedFilter == "Q.7") "65M" else "150M"
                    val emptyRooms = if(selectedFilter == "Q.1") "01" else if(selectedFilter == "Q.7") "02" else "04"
                    
                    MetricCard(label = "Doanh thu", value = revenue, icon = Icons.Default.AttachMoney, color = Color(0xFF2E7D32), modifier = Modifier.weight(1f))
                    MetricCard(label = "Phòng trống", value = emptyRooms, icon = Icons.Default.MeetingRoom, color = Color(0xFFEF6C00), modifier = Modifier.weight(1f))
                    MetricCard(label = "Sự cố", value = "01", icon = Icons.Default.ReportProblem, color = Color(0xFFC62828), modifier = Modifier.weight(1f))
                }
            }

            // Gộp lại thành một biểu đồ phân tích duy nhất (Pie Chart)
            item {
                SectionTitle(title = "Phân tích doanh thu $selectedFilter", onActionClick = {
                    navController.navigate("expenditures/$selectedFilter")
                })
                RevenuePieChartBox(selectedFilter)
            }

            // Danh sách cơ sở
            item {
                SectionTitle(title = "Danh sách cơ sở")
                Column(verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)) {
                    PropertySummaryItem(
                        name = "M-Motel Quận 1", 
                        address = "123 Quận 1, TP.HCM", 
                        rooms = 5, 
                        occupied = 4,
                        onClick = { navController.navigate("motel_detail/M-Motel Quận 1/123 Quận 1, TP.HCM") }
                    )
                    PropertySummaryItem(
                        name = "M-Motel Quận 7", 
                        address = "456 Quận 7, TP.HCM", 
                        rooms = 5, 
                        occupied = 3,
                        onClick = { navController.navigate("motel_detail/M-Motel Quận 7/456 Quận 7, TP.HCM") }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun RevenuePieChartBox(filter: String) {
    // Dữ liệu giả lập khác nhau cho từng chi nhánh
    val roomPercent = if(filter == "Q.1") 252f else if(filter == "Q.7") 180f else 210f
    val utilityPercent = if(filter == "Q.1") 72f else if(filter == "Q.7") 120f else 90f
    val servicePercent = 360f - roomPercent - utilityPercent

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppVisualizeBg),
        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Vẽ Pie Chart
            Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawArc(color = Color(0xFF4CAF50), startAngle = 0f, sweepAngle = roomPercent, useCenter = true)
                    drawArc(color = Color(0xFF2196F3), startAngle = roomPercent, sweepAngle = utilityPercent, useCenter = true)
                    drawArc(color = Color(0xFFFFC107), startAngle = roomPercent + utilityPercent, sweepAngle = servicePercent, useCenter = true)
                }
                Surface(modifier = Modifier.size(60.dp), shape = CircleShape, color = AppVisualizeBg) {}
            }

            // Chú thích
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ChartLegendItem(color = Color(0xFF4CAF50), label = "Phòng (${(roomPercent/3.6).toInt()}%)")
                ChartLegendItem(color = Color(0xFF2196F3), label = "Điện nước (${(utilityPercent/3.6).toInt()}%)")
                ChartLegendItem(color = Color(0xFFFFC107), label = "Dịch vụ (${(servicePercent/3.6).toInt()}%)")
            }
        }
    }
}

@Composable
fun ChartLegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun PropertySummaryItem(name: String, address: String, rooms: Int, occupied: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick, 
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(AppDimensions.RadiusMedium),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(AppPrimaryBlue.copy(0.1f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Home, contentDescription = null, tint = AppPrimaryBlue)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = address, color = Color.Gray, fontSize = AppTypography.SizeSmall)
            }
            Text(text = "$occupied/$rooms", fontWeight = FontWeight.Bold, color = AppPrimaryBlue)
        }
    }
}
