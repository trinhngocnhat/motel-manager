package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
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
fun OwnerContractManagementScreen(
    navController: NavController,
    viewModel: OwnerViewModel = viewModel()
) {
    val contracts by viewModel.allContracts.collectAsState()

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "QUẢN LÝ HỢP ĐỒNG",
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
            SectionTitle(title = "Danh sách Hợp đồng Thuê")

            val headers = listOf("Hợp đồng", "Phòng", "Người thuê chính", "Trạng thái")
            val rows = contracts.map { contract ->
                listOf(contract.contractId, contract.roomId, contract.userId, contract.status)
            }

            if (rows.isNotEmpty()) {
                MotelTable(headers = headers, rows = rows)
            } else {
                Text("Chưa có hợp đồng nào.")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("owner_add_tenant_to_contract") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm người ở vào Hợp đồng")
            }
        }
    }
}
