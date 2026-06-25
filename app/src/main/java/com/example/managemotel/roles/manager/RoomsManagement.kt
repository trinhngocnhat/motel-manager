package com.example.managemotel.roles.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.models.MotelRoom
import com.example.managemotel.ui.theme.AppDimensions
import kotlinx.coroutines.launch

@Composable
fun RoomsManagementScreen(navController: NavController, viewModel: com.example.managemotel.viewmodels.RoomViewModel = viewModel()) {
    val roomsState by viewModel.allRoomsWithTenants.collectAsState(initial = emptyList())

    RoomsManagementContent(
        navController = navController,
        rooms = roomsState,
        onSync = { viewModel.syncRoomsFromBackend() },
        onUpdateRoomType = { roomId, newType, currentStatus, currentPrice ->
            saveRoomToDatabase(viewModel, mutableListOf(roomId, currentPrice), newType, currentStatus)
        },
        onUpdatePaymentStatus = { roomId ->
            viewModel.updatePaymentStatus(roomId, "Đã đóng")
        }
    )
}

@Composable
fun RoomsManagementContent(
    navController: NavController,
    rooms: List<com.example.managemotel.models.RoomWithTenant>,
    onSync: () -> Unit = {},
    onUpdateRoomType: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onUpdatePaymentStatus: (String) -> Unit = {}
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        onSync()
    }
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
            MotelRoomCardList(
                rooms = rooms,
                onUpdateRoomType = onUpdateRoomType,
                onUpdatePaymentStatus = onUpdatePaymentStatus
            )
        }
    }
}

@Composable
fun MotelRoomCardList(
    rooms: List<com.example.managemotel.models.RoomWithTenant>,
    onUpdateRoomType: (String, String, String, String) -> Unit,
    onUpdatePaymentStatus: (String) -> Unit
) {
    val rows = remember(rooms) {
        val list = mutableStateListOf<MutableList<String>>()
        rooms.forEach { room: com.example.managemotel.models.RoomWithTenant ->
            val priceStr = room.price.toInt().toString()
            val tenant = room.tenantName ?: "---"
            list.add(mutableListOf(room.roomId, priceStr, tenant, room.typeRooms, room.status))
        }
        list
    }

    // State để quản lý việc mở Dialog và lưu chỉ số phòng đang được chọn
    var selectedIndex by remember { mutableStateOf(-1) }
    var showEditTypeDialog by remember { mutableStateOf(false) }
    var showConfirmPayDialog by remember { mutableStateOf(false) }

    val headers = listOf("Phòng", "Giá thuê", "Khách", "Loại", "Trạng thái")

    Column(modifier = Modifier.fillMaxSize()) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(headers[0], modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
            Text(headers[1], modifier = Modifier.weight(1.2f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
            Text(headers[2], modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
            Text(headers[3], modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
            Text(headers[4], modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(rows) { index, row ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedIndex = if (selectedIndex == index) -1 else index
                        }
                        .background(if (selectedIndex == index) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(row[0], modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        Text("${row[1]}đ", modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodySmall)
                        Text(row[2], modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodySmall, maxLines = 1)
                        Text(row[3], modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.bodySmall, maxLines = 1)
                        
                        // Status with Color mapping
                        val statusText = row[4]
                        val (statusLabel, statusColor) = when (statusText) {
                            "AVAILABLE" -> "Trống" to Color(0xFF4CAF50)
                            "OCCUPIED" -> "Đang ở" to Color(0xFFF44336)
                            "MAINTENANCE" -> "Bảo trì" to Color(0xFFFFC107)
                            else -> statusText to Color.Gray
                        }

                        Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.CenterStart) {
                            Surface(
                                color = statusColor.copy(alpha = 0.1f),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = statusLabel,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                    color = statusColor,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    
                    // Quick Action Buttons when selected
                    if (selectedIndex == index) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { showEditTypeDialog = true }) {
                                Text("Sửa", style = MaterialTheme.typography.labelSmall)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { showConfirmPayDialog = true },
                                // Assume we can only pay for occupied rooms or based on some logic
                                enabled = row[4] == "OCCUPIED",
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Đóng tiền", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                    androidx.compose.material3.HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
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
                        val newType = "màu xanh biên (loại thường)"
                        updateRoomData(rows, selectedIndex, 3, newType)
                        
                        // SAVE TO DATABASE
                        onUpdateRoomType(rows[selectedIndex][0], newType, rows[selectedIndex][4], rows[selectedIndex][1])
                        
                        showEditTypeDialog = false
                    }) { Text("Màu xanh biên (Loại thường)") }

                    TextButton(onClick = {
                        val newType = "màu vàng (có nội thất)"
                        updateRoomData(rows, selectedIndex, 3, newType)
                        
                        // SAVE TO DATABASE
                        onUpdateRoomType(rows[selectedIndex][0], newType, rows[selectedIndex][4], rows[selectedIndex][1])
                        
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
                    val newStatus = "tích xanh (đã đóng tin)"
                    
                    // 1. Cập nhật UI ngay lập tức
                    updateRoomData(rows, selectedIndex, 4, newStatus)
                    
                    // 2. Lưu vào Database
                    val roomId = rows[selectedIndex][0]
                    onUpdatePaymentStatus(roomId)

                    showConfirmPayDialog = false
                }) { Text("Xác nhận", color = Color(0xFF4CAF50)) }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmPayDialog = false }) { Text("Hủy") }
            }
        )
    }
}

// Hàm hỗ trợ lưu vào Database
fun saveRoomToDatabase(
    viewModel: com.example.managemotel.viewmodels.RoomViewModel,
    row: MutableList<String>,
    typeDesc: String,
    statusDesc: String
) {
    val roomId = row[0]
    val status = statusDesc // AVAILABLE, OCCUPIED, MAINTENANCE
    
    viewModel.viewModelScope.launch {
        val existingRoom = viewModel.getRoomById(roomId)
        if (existingRoom != null) {
            viewModel.update(existingRoom.copy(
                typeRooms = typeDesc,
                status = status
            ))
        } else {
            viewModel.insert(com.example.managemotel.models.MotelRoom(
                roomId = roomId,
                typeRooms = typeDesc,
                status = status,
                floor = 1,
                note = typeDesc,
                price = row[1].toDoubleOrNull() ?: 0.0,
                managerId = null,
//                paymentStatus = if (status == "OCCUPIED") "Đã đóng" else "Chưa thanh toán"
            ))
        }
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
    RoomsManagementContent(
        navController = navController,
        rooms = listOf(
            com.example.managemotel.models.RoomWithTenant(
                roomId = "101",
                price = 2500000.0,
                tenantName = "Nguyễn Văn A",
                typeRooms = "Thường",
                status = "OCCUPIED",
                tenantPhone = "0123456789",
                paymentStatus = "Đã đóng"
            ),
            com.example.managemotel.models.RoomWithTenant(
                roomId = "102",
                price = 3000000.0,
                tenantName = null,
                typeRooms = "Vip",
                status = "AVAILABLE",
                tenantPhone = null,
                paymentStatus = null
            )
        )
    )
}