package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.components.MotelTable
import com.example.managemotel.components.SectionTitle
import com.example.managemotel.ui.theme.AppDimensions
import com.example.managemotel.viewmodels.OwnerViewModel

@Composable
fun OwnerAccountManagementScreen(
    navController: NavController,
    viewModel: OwnerViewModel = viewModel()
) {
    val users by viewModel.managedUsers.collectAsState()

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "QUẢN LÝ TÀI KHOẢN",
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
            SectionTitle(title = "Danh sách Quản lý & Người thuê")

            val headers = listOf("ID", "Họ tên", "Vai trò", "Email")
            val rows = users.map { user ->
                listOf(user.userId, user.fullName, user.role, user.email ?: "---")
            }

            if (rows.isNotEmpty()) {
                MotelTable(headers = headers, rows = rows)
            } else {
                Text("Chưa có tài khoản nào được quản lý.")
            }
            
            // Note: In a full app, we would add buttons here for Create, Update, Delete
            // with dialogs to input user data.
        }
    }
}
