package com.example.managemotel.roles.manager

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
fun IssueManagementScreen(navController: NavController) {
    Scaffold(
        topBar = { CommonHeader(roleName = "QUẢN LÝ SỰ CỐ", navController = navController, showBackButton = true) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            Text(text = "Trang quản lý sự cố")
            // TODO: Add Issue Management UI components here
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IssueManagementScreenPreview() {
    val navController = rememberNavController()
    IssueManagementScreen(navController = navController)
}
