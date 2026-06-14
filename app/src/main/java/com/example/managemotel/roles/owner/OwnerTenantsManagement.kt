package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.ui.theme.AppDimensions

@Composable
fun OwnerTenantsManagementScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "CHỦ NHÀ - KHÁCH THUÊ", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            Text(text = "Trang quản lý khách thuê của chủ nhà")
            OwnerTenantCardList()
        }
    }
}

@Composable
fun OwnerTenantCardList() {
    val headers = listOf("Phòng", "Giá thuê", "Người Thuê", "Trạng thái")
    val rows = listOf(
        listOf("P.101", "1380", "Mạnh Hùng", "Đã thanh toán"),
        listOf("P.102", "890", "Quang Phát", "Đang chờ"),
        listOf("P.201", "2100", "Ngọc Linh", "Quá hạn"),
        listOf("P.202", "300", "Linh Chi", "Quá hạn"),
        listOf("P.301", "1540", "Hoàng Kha", "Sắp tới hạn")
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
fun OwnerTenantsManagementScreenPreview() {
    val navController = rememberNavController()
    OwnerTenantsManagementScreen(navController = navController)
}
