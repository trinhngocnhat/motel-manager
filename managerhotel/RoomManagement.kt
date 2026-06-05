package com.example.managemotel.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.ui.theme.*
import java.util.Locale

// SQLITE: Đây sẽ là Entity đại diện cho bảng 'rooms' trong database
data class Room(
    val id: String, // Khóa chính (Primary Key)
    val status: RoomStatus,
    val tenantName: String? = null,
    val phoneNumber: String? = null,
    val lastElectricity: Int = 0,
    val lastWater: Int = 0,
    val paymentStatus: String = "Chưa đóng",
    val rentalType: String = "Dài hạn", // Ngắn hạn / Dài hạn
    val furnishing: String = "Có vật tư", // Có vật tư / Ko vật tư
    val electricityPrice: Int = 3500,
    val waterPrice: Int = 20000,
    val deposit: Long = 3000000,
    val rentalStartDate: String = "01/01/2024",
    val rentalEndDate: String = "01/06/2025",
    val furnitureList: List<String> = listOf("Giường ngủ", "Tủ quần áo", "Máy lạnh", "Tủ lạnh", "Bàn làm việc")
)

enum class RoomStatus(val label: String, val color: Color) {
    AVAILABLE("Phòng trống", Color(0xFF4CAF50)),
    OCCUPIED("Đang ở", Color(0xFFF44336)),
    MAINTENANCE("Bảo trì", Color(0xFF9E9E9E))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomManagementScreen(navController: NavController) {
    // SQLITE: Tại đây bạn sẽ sử dụng ViewModel để lấy dữ liệu thực từ SQLite
    // Dưới đây là 2 phòng ảo để bạn xem trước sự khác biệt
    val rooms = listOf(
        Room(
            id = "101",
            status = RoomStatus.OCCUPIED,
            tenantName = "Nguyễn Văn A",
            phoneNumber = "0912345678",
            lastElectricity = 1250,
            lastWater = 460,
            paymentStatus = "Chưa đóng",
            rentalType = "Dài hạn",
            furnishing = "Có nội thất",
            furnitureList = listOf("Giường ngủ", "Tủ quần áo", "Máy lạnh")
        ),
        Room(
            id = "102",
            status = RoomStatus.AVAILABLE,
            tenantName = null,
            phoneNumber = null,
            lastElectricity = 0,
            lastWater = 0,
            paymentStatus = "---",
            rentalType = "Chưa thuê",
            furnishing = "Phòng trống",
            furnitureList = emptyList() // PHÒNG KHÔNG NỘI THẤT
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CHI TIẾT TRẠNG THÁI PHÒNG", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rooms) { room ->
                DetailedRoomCard(
                    room = room,
                    onBillClick = { navController.navigate("create_bill/${room.id}") },
                    onDetailClick = { navController.navigate("room_detail/${room.id}") }
                )
            }
        }
    }
}

@Composable
fun DetailedRoomCard(room: Room, onBillClick: () -> Unit, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Số phòng và Trạng thái
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Phòng ${room.id}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppPrimaryBlue
                )
                Surface(
                    color = room.status.color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = room.status.label,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        color = room.status.color,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

            // Body: Thông tin khách và Điện nước
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1.1f)) {
                    InfoRow(Icons.Default.Person, room.tenantName ?: "Phòng trống")
                    InfoRow(Icons.Default.Phone, room.phoneNumber ?: "---")
                    InfoRow(
                        Icons.Default.MonetizationOn, 
                        room.paymentStatus, 
                        if(room.paymentStatus == "Đã đóng") Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
                Column(modifier = Modifier.weight(0.9f)) {
                    InfoRow(Icons.Default.FlashOn, "Điện: ${room.lastElectricity} kW")
                    InfoRow(Icons.Default.WaterDrop, "Nước: ${room.lastWater} m³")
                    InfoRow(Icons.Default.CalendarToday, room.rentalType)
                    InfoRow(Icons.Default.Chair, room.furnishing)
                }
            }

            // Quick Actions Footer
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallActionButton(
                    icon = Icons.AutoMirrored.Filled.ReceiptLong, 
                    label = "Hóa đơn",
                    onClick = { onBillClick() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                SmallActionButton(
                    icon = Icons.Outlined.Info, 
                    label = "Chi tiết",
                    onClick = { onDetailClick() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailScreen(navController: NavController, roomId: String) {
    // SQLITE: Lấy thông tin chi tiết của phòng từ Database dựa trên roomId
    val room = Room(
        id = roomId,
        status = RoomStatus.OCCUPIED,
        tenantName = "Nguyễn Văn A",
        phoneNumber = "0912345678",
        lastElectricity = 1250,
        lastWater = 460
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CHI TIẾT PHÒNG $roomId", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppPrimaryBlue, titleContentColor = Color.White, navigationIconContentColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF5F5F5)).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Thông tin khách hàng
            DetailSection(title = "Thông tin khách thuê", icon = Icons.Default.Person) {
                DetailRow("Họ và tên", room.tenantName ?: "---")
                DetailRow("Số điện thoại", room.phoneNumber ?: "---")
                DetailRow("Ngày bắt đầu", room.rentalStartDate)
                DetailRow("Thời hạn hợp đồng", room.rentalEndDate)
                DetailRow("Tiền cọc", String.format(Locale.US, "%,dđ", room.deposit))
            }

            // 2. Thông tin giá & Dịch vụ
            DetailSection(title = "Giá dịch vụ", icon = Icons.Default.Payments) {
                DetailRow("Giá điện", "${room.electricityPrice} đ/kW")
                DetailRow("Giá nước", "${room.waterPrice} đ/m³")
                DetailRow("Loại hình", room.rentalType)
            }

            // 3. Danh mục vật tư nội thất
            DetailSection(title = "Danh mục nội thất", icon = Icons.Default.Chair) {
                if (room.furnitureList.isEmpty()) {
                    Text(
                        "Phòng cơ bản, chưa có trang bị nội thất.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        room.furnitureList.forEach { item ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF4CAF50))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(item, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailSection(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = AppPrimaryBlue, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.Bold, color = AppPrimaryBlue)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
            content()
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        Text(value, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String, textColor: Color = Color.Gray) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium, color = textColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun SmallActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Surface(onClick = onClick, color = AppPrimaryBlue.copy(alpha = 0.05f), shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = AppPrimaryBlue)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, color = AppPrimaryBlue, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomManagementPreview() {
    RoomManagementScreen(rememberNavController())
}
