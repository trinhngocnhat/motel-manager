package com.example.managemotel.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

            // Công việc hôm nay
            item {
                SectionTitle(title = "Công việc hôm nay")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        TodayTaskRow(
                            icon = Icons.Default.Warning,
                            task = "Phòng 104 báo hỏng điều hòa",
                            time = "08:30",
                            color = Color(0xFFC62828),
                            onClick = { navController.navigate("room_detail/104") }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                        TodayTaskRow(
                            icon = Icons.AutoMirrored.Filled.ExitToApp,
                            task = "Phòng 102 thực hiện trả phòng",
                            time = "10:00",
                            color = AppPrimaryBlue,
                            onClick = { navController.navigate("room_detail/102") }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                        TodayTaskRow(
                            icon = Icons.Default.Schedule,
                            task = "Hẹn khách xem phòng 205",
                            time = "14:00",
                            color = Color(0xFFEF6C00),
                            onClick = { navController.navigate("manage_rooms") }
                        )
                    }
                }
            }

            // Chức năng nhanh
            item {
                SectionTitle(title = "Chức năng nhanh")
                Column(verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                    ) {
                        QuickActionButton(
                            text = "QL Phòng",
                            icon = Icons.Default.HomeWork,
                            containerColor = AppCardBlue,
                            onClick = { navController.navigate("manage_rooms") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionButton(
                            text = "QL Doanh thu",
                            subText = "+45.000.000đ", // HIỂN THỊ GIÁ TIỀN ĐẦU MỤC
                            subTextColor = Color(0xFF2E7D32),
                            icon = Icons.Default.MonetizationOn,
                            containerColor = AppCardGreen,
                            onClick = { navController.navigate("revenue") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                    ) {
                        QuickActionButton(
                            text = "QL Chi phí",
                            subText = "-12.500.000đ", // HIỂN THỊ GIÁ TIỀN ĐẦU MỤC
                            subTextColor = Color(0xFFC62828),
                            icon = Icons.Default.Payments,
                            containerColor = AppCardOrange,
                            onClick = { navController.navigate("expenses") },
                            modifier = Modifier.weight(1f)
                        )
                        QuickActionButton(
                            text = "QL Yêu cầu",
                            icon = Icons.AutoMirrored.Filled.Assignment,
                            containerColor = AppCardRed,
                            onClick = { navController.navigate("requests") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Tiến độ công việc
            item {
                SectionTitle(title = "Tiến độ công việc", onActionClick = { /* TODO: Xem chi tiết */ })
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ProgressItem(
                            label = "Thu tiền phòng",
                            current = 15,
                            total = 20,
                            color = Color(0xFF4CAF50)
                        )
                        ProgressItem(
                            label = "Ghi điện nước",
                            current = 18,
                            total = 20,
                            color = AppPrimaryBlue
                        )
                        ProgressItem(
                            label = "Xử lý yêu cầu",
                            current = 1,
                            total = 3,
                            color = Color(0xFFFF9800)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge)) }
        }
    }
}

@Composable
fun TodayTaskRow(icon: ImageVector, task: String, time: String, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp, 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = color)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = task, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(text = "Thời gian: $time", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray)
    }
}

@Composable
fun ProgressItem(label: String, current: Int, total: Int, color: Color) {
    val progress = current.toFloat() / total.toFloat()
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(
                text = "$current/$total phòng",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = color,
            trackColor = color.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun QuickActionButton(
    text: String,
    subText: String? = null,
    subTextColor: Color = Color.Gray,
    icon: ImageVector,
    containerColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
        contentPadding = PaddingValues(AppDimensions.PaddingSmall)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp),
                tint = AppPrimaryBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            if (subText != null) {
                Text(
                    text = subText,
                    style = MaterialTheme.typography.labelSmall,
                    color = subTextColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManagerScreenPreview() {
    ManageMotelTheme {
        ManagerScreen(navController = rememberNavController())
    }
}
