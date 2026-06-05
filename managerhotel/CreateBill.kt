package com.example.managemotel.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.ui.theme.*
import androidx.compose.foundation.text.KeyboardOptions
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBillScreen(navController: NavController, roomId: String = "101") {
    var depositAmount by remember { mutableStateOf("") }
    var fineAmount by remember { mutableStateOf("") }
    var fineReason by remember { mutableStateOf("") }
    
    val roomPrice = 3500000
    val servicePrice = 150000

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("LẬP HÓA ĐƠN PHÒNG $roomId", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppPrimaryBlue, titleContentColor = Color.White, navigationIconContentColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Thông tin phòng
            BillSectionCard(title = "Thông tin phòng", icon = Icons.Default.Home) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Phòng: $roomId", fontWeight = FontWeight.Bold)
                    Text("Khách: Nguyễn Văn A", color = Color.Gray)
                }
            }

            // Tiền cọc (Check-in)
            BillSectionCard(title = "Khoản thu đặt cọc", icon = Icons.Default.Savings) {
                OutlinedTextField(
                    value = depositAmount,
                    onValueChange = { depositAmount = it },
                    label = { Text("Số tiền cọc (VNĐ)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    prefix = { Text("đ ") },
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                )
            }

            // Tiền phạt & Phụ phí
            BillSectionCard(title = "Phụ phí & Tiền phạt", icon = Icons.Default.ReportProblem) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = fineAmount,
                        onValueChange = { fineAmount = it },
                        label = { Text("Số tiền phạt/phụ phí") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        prefix = { Text("đ ") },
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                    )
                    OutlinedTextField(
                        value = fineReason,
                        onValueChange = { fineReason = it },
                        label = { Text("Lý do (VD: Hỏng khóa cửa, Trễ hạn...)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                    )
                }
            }

            // Chi phí cố định
            BillSectionCard(title = "Chi phí hàng tháng", icon = Icons.AutoMirrored.Filled.ListAlt) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    PriceRow("Tiền phòng", roomPrice)
                    PriceRow("Dịch vụ cố định (Wifi, Rác)", servicePrice)
                }
            }

            // Tổng cộng
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppPrimaryBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                val total = roomPrice + servicePrice + (fineAmount.toIntOrNull() ?: 0) + (depositAmount.toIntOrNull() ?: 0)
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("TỔNG THANH TOÁN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("${String.format(Locale.US, "%,d", total)}đ", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
            }

            Button(
                onClick = { /* TODO: Save & Print */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Icon(Icons.Default.Print, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("XUẤT HÓA ĐƠN & GỬI KHÁCH", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BillSectionCard(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = AppPrimaryBlue, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, color = AppPrimaryBlue)
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun PriceRow(label: String, price: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray)
        Text(text = "${String.format(Locale.US, "%,d", price)}đ", fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun CreateBillPreview() {
    CreateBillScreen(rememberNavController())
}
