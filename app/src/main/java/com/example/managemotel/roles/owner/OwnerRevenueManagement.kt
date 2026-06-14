package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.ui.theme.AppDimensions

@Composable
fun OwnerRevenueManagementScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "CHỦ NHÀ - DOANH THU", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            Text(text = "Trang quản lý doanh thu của chủ nhà")
            // TODO: Add Owner Revenue Management UI components here
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerRevenueManagementScreenPreview() {
    val navController = rememberNavController()
    OwnerRevenueManagementScreen(navController = navController)
}
