package com.example.managemotel.roles.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            MotelRoomCardList() // call the card list here
        }
    }
}

@Composable
fun MotelRoomCardList() {
    val headers = listOf("Phòng", "Giá thuê", "Người Thuê", "Trạng thái")
    val rows = listOf(
        listOf("P.101 - màu xanh biên (loại thường)", "1380", "Mạnh Hùng", "tích xanh (đã đóng tin)"),
        listOf("P.102 - màu vàng (có nội thất)", "890", "Quang Phát", "tích vàng chưa đến hạn"),
        listOf("P.201 - màu vàng (có nội thất)", "2100", "Ngọc Linh", "tích đỏ nợ chưa trả"),
        listOf("P.202 - màu xanh biên (loại thường)", "300", "Linh Chi", "tích đỏ nợ chưa trả"),
        listOf("P.301 - màu xanh biên (loại thường)", "1540", "Hoàng Kha", "tích vàng chưa đến hạn")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(rows) { row ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "${headers[0]}: ${row[0]}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "${headers[1]}: ${row[1]} VNĐ", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "${headers[2]}: ${row[2]}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "${headers[3]}: ${row[3]}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomsManagementScreenPreview() {
    val navController = rememberNavController()
    RoomsManagementScreen(navController = navController)
}
