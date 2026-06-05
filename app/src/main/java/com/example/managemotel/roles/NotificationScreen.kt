package com.example.managemotel.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.components.SectionTitle
import com.example.managemotel.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(navController: NavController) {

    // NEW - Danh sách loại yêu cầu
    val requestTypes = listOf(
        "Gia hạn hợp đồng",
        "Sửa chữa điện",
        "Sửa chữa nước",
        "Sửa chữa internet",
        "Sửa chữa thiết bị phòng",
        "Vệ sinh khu vực chung",
        "Khiếu nại",
        "Yêu cầu khác"
    )

    // NEW - State form nhập liệu
    var selectedType  by remember { mutableStateOf(requestTypes[0]) }
    var expanded      by remember { mutableStateOf(false) }
    var title         by remember { mutableStateOf("") }
    var description   by remember { mutableStateOf("") }
    var showSuccess   by remember { mutableStateOf(false) }

    // NEW - Ngày gửi tự động lấy từ thiết bị
    val sentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    // NEW - Dialog thông báo gửi thành công
    if (showSuccess) {
        AlertDialog(
            onDismissRequest = {},
            icon = {
                Icon(
                    Icons.Default.Send,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32)
                )
            },
            title = {
                Text("Gửi thành công!", fontWeight = FontWeight.Bold)
            },
            text = {
                Text("Yêu cầu của bạn đã được gửi tới quản lý. Chúng tôi sẽ phản hồi sớm nhất có thể.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showSuccess = false
                    navController.popBackStack()
                }) {
                    Text("Quay về", color = Color(0xFF2E7D32))
                }
            },
            shape = RoundedCornerShape(AppDimensions.RadiusLarge)
        )
    }

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "GỬI YÊU CẦU",
                navController = navController,
                showBackButton = true
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
        ) {
            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall)) }

            item { SectionTitle(title = "Thông tin yêu cầu") }

            // NEW - Dropdown chọn loại yêu cầu
            item {
                Text(
                    text = "Loại yêu cầu",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6A1B9A),
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        requestTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type, fontSize = 13.sp) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // NEW - Tiêu đề yêu cầu
            item {
                Text(
                    text = "Tiêu đề",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("Nhập tiêu đề yêu cầu...", fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A1B9A),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            // NEW - Nội dung mô tả
            item {
                Text(
                    text = "Nội dung mô tả",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Mô tả chi tiết yêu cầu của bạn...", fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A1B9A),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            // NEW - Ngày gửi tự động
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Ngày gửi", fontSize = 13.sp, color = Color.Gray)
                    Text(sentDate, fontSize = 13.sp, fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface)
                }
            }

            // NEW - Nút gửi yêu cầu
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { showSuccess = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A1B9A)
                    ),
                    enabled = title.isNotBlank() && description.isNotBlank()
                ) {
                    Icon(Icons.Default.Send, contentDescription = null,
                        modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gửi yêu cầu", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))
            }
        }
    }
}
