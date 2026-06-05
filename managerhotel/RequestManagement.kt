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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.ui.theme.*

enum class RequestStatus(val label: String, val color: Color) {
    PENDING("Chờ xử lý", Color(0xFFF44336)),
    IN_PROGRESS("Đang xử lý", Color(0xFFFF9800)),
    COMPLETED("Đã hoàn thành", Color(0xFF4CAF50))
}

data class TenantRequest(
    val id: String,
    val roomId: String,
    val tenantName: String,
    val description: String,
    val date: String,
    val status: RequestStatus
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestManagementScreen(navController: NavController) {
    // SQLITE: Dữ liệu này sẽ được lấy từ bảng 'requests' trong database
    val requests = listOf(
        TenantRequest("1", "101", "Nguyễn Văn A", "Hỏng vòi nước bồn rửa mặt", "15/06/2024", RequestStatus.PENDING),
        TenantRequest("2", "205", "Trần Thị B", "Bóng đèn hành lang bị cháy", "14/06/2024", RequestStatus.IN_PROGRESS),
        TenantRequest("3", "302", "Lê Văn C", "Wifi phòng yếu, hay bị rớt mạng", "13/06/2024", RequestStatus.COMPLETED),
        TenantRequest("4", "104", "Phạm Văn D", "Cửa sổ bị kẹt không đóng được", "12/06/2024", RequestStatus.PENDING)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("QUẢN LÝ YÊU CẦU", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
        ) {
            // Bộ lọc nhanh
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(selected = true, onClick = {}, label = { Text("Tất cả") })
                FilterChip(selected = false, onClick = {}, label = { Text("Chờ xử lý") })
                FilterChip(selected = false, onClick = {}, label = { Text("Đã xong") })
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(requests) { request ->
                    RequestCard(request)
                }
            }
        }
    }
}

@Composable
fun RequestCard(request: TenantRequest) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Phòng ${request.roomId} - ${request.tenantName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = AppPrimaryBlue
                )
                Surface(
                    color = request.status.color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = request.status.label,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = request.status.color,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = request.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gửi ngày: ${request.date}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (request.status != RequestStatus.COMPLETED) {
                        TextButton(
                            onClick = { /* SQLITE: Cập nhật trạng thái thành Đã hoàn thành */ },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Hoàn thành", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestManagementPreview() {
    RequestManagementScreen(rememberNavController())
}
