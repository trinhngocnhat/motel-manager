package com.example.managemotel.roles.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.ui.theme.AppDimensions

@Composable
fun RoomsManagementScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "QUẢN LÝ PHÒNG TRỌ",
                navController = navController,
                showBackButton = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            Text(text = "Trang quản lý phòng trọ")
            MotelRoomCardList()
        }
    }
}

@Composable
fun MotelRoomCardList() {
    // 1. Chuyển sang mutableStateListOf để có thể sửa dữ liệu và UI tự động cập nhật
    val rows = remember {
        mutableStateListOf(
            mutableListOf("P.101", "1380", "Mạnh Hùng", "màu xanh biên (loại thường)", "tích xanh (đã đóng tin)"),
            mutableListOf("P.102", "890", "Quang Phát", "màu vàng (có nội thất)", "tích vàng chưa đến hạn"),
            mutableListOf("P.201", "2100", "Ngọc Linh", "màu vàng (có nội thất)", "tích đỏ nợ chưa trả"),
            mutableListOf("P.202", "300", "Linh Chi", "màu xanh biên (loại thường)", "tích đỏ nợ chưa trả"),
            mutableListOf("P.301", "1540", "Hoàng Kha", "màu xanh biên (loại thường)", "tích vàng chưa đến hạn")
        )
    }

    // State để quản lý việc mở Dialog và lưu chỉ số phòng đang được chọn
    var selectedIndex by remember { mutableStateOf(-1) }
    var showEditTypeDialog by remember { mutableStateOf(false) }
    var showConfirmPayDialog by remember { mutableStateOf(false) }

    val headers = listOf("Phòng", "Giá thuê", "Người Thuê", "Loại Phòng", "Trạng thái")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 2. Dùng itemsIndexed để lấy cả index (vị trí) của phòng
        itemsIndexed(rows) { index, row ->

            val cardColor = when {
                row[3].contains("xanh biên", ignoreCase = true) -> Color(0xFFBBDEFB)
                row[3].contains("vàng", ignoreCase = true) -> Color(0xFFFFF59D)
                else -> MaterialTheme.colorScheme.surface
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${headers[0]}: ${row[0].trim()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "${headers[1]}: ${row[1]} VNĐ", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "${headers[2]}: ${row[2]}", style = MaterialTheme.typography.bodyMedium)

                    // Phần Trạng thái (Icon)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "${headers[4]}:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        when {
                            row[4].contains("xanh", ignoreCase = true) -> {
                                Icon(Icons.Filled.Check, "Đã đóng", tint = Color(0xFF4CAF50), modifier = Modifier.size(18.dp))
                                Text("Đã đóng", color = Color(0xFF2E7D32), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                            }
                            row[4].contains("vàng", ignoreCase = true) -> {
                                Icon(Icons.Filled.Warning, "Chưa đến hạn", tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                                Text("Chưa đến hạn", color = Color(0xFFF57F17), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                            }
                            row[4].contains("đỏ", ignoreCase = true) -> {
                                Icon(Icons.Filled.Error, "Nợ chưa trả", tint = Color(0xFFF44336), modifier = Modifier.size(18.dp))
                                Text("Nợ chưa trả", color = Color(0xFFC62828), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                    // 3. THÊM 2 NÚT CHỨC NĂNG
                    Spacer(modifier = Modifier.height(8.dp)) // Tạo khoảng cách với phần thông tin
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Nút Sửa loại phòng
                        OutlinedButton(
                            onClick = {
                                selectedIndex = index
                                showEditTypeDialog = true
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Sửa phòng", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                        }

                        // Nút Đóng tiền mặt
                        Button(
                            onClick = {
                                selectedIndex = index
                                showConfirmPayDialog = true
                            },
                            modifier = Modifier.weight(1f),
                            enabled = !row[4].contains("xanh", ignoreCase = true) // Ẩn nút nếu đã đóng rồi
                        ) {
                            Text("Đóng tiền", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                        }
                    }
                }
            }
        }
    }

    // 4. DIALOG CHỌN LOẠI PHÒNG
    if (showEditTypeDialog && selectedIndex != -1) {
        AlertDialog(
            onDismissRequest = { showEditTypeDialog = false },
            title = { Text("Chọn loại phòng cho ${rows[selectedIndex][0]}") },
            text = {
                Column {
                    TextButton(onClick = {
                        updateRoomData(rows, selectedIndex, 3, "màu xanh biên (loại thường)")
                        showEditTypeDialog = false
                    }) { Text("Màu xanh biên (Loại thường)") }

                    TextButton(onClick = {
                        updateRoomData(rows, selectedIndex, 3, "màu vàng (có nội thất)")
                        showEditTypeDialog = false
                    }) { Text("Màu vàng (Có nội thất)") }
                }
            },
            confirmButton = {
                TextButton(onClick = { showEditTypeDialog = false }) { Text("Đóng") }
            }
        )
    }

    // 5. DIALOG XÁC NHẬN ĐÓNG TIỀN
    if (showConfirmPayDialog && selectedIndex != -1) {
        AlertDialog(
            onDismissRequest = { showConfirmPayDialog = false },
            title = { Text("Xác nhận đóng tiền") },
            text = { Text("Xác nhận phòng ${rows[selectedIndex][0]} đã đóng tiền mặt?") },
            confirmButton = {
                TextButton(onClick = {
                    // Đổi trạng thái sang tích xanh
                    updateRoomData(rows, selectedIndex, 4, "tích xanh (đã đóng tin)")
                    showConfirmPayDialog = false
                }) { Text("Xác nhận", color = Color(0xFF4CAF50)) }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmPayDialog = false }) { Text("Hủy") }
            }
        )
    }
}

// Hàm hỗ trợ cập nhật dữ liệu an toàn trong Compose
fun updateRoomData(rows: MutableList<MutableList<String>>, index: Int, columnIndex: Int, newValue: String) {
    val updatedRow = rows[index].toMutableList()
    updatedRow[columnIndex] = newValue
    rows[index] = updatedRow // Gán lại list để Compose biết dữ liệu đã thay đổi và vẽ lại UI
}

@Preview(showBackground = true)
@Composable
fun RoomsManagementScreenPreview() {
    val navController = rememberNavController()
    RoomsManagementScreen(navController = navController)
}