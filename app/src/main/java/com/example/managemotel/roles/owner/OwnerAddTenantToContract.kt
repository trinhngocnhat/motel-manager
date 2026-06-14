package com.example.managemotel.roles.owner

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.ui.theme.AppDimensions
import com.example.managemotel.viewmodels.OwnerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerAddTenantToContractScreen(
    navController: NavController,
    viewModel: OwnerViewModel = viewModel()
) {
    val contracts by viewModel.allContracts.collectAsState()
    val tenants by viewModel.allTenants.collectAsState()
    val context = LocalContext.current

    var selectedContractId by remember { mutableStateOf("") }
    var selectedTenantId by remember { mutableStateOf("") }
    var expandedContract by remember { mutableStateOf(false) }
    var expandedTenant by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "THÊM NGƯỜI VÀO HỢP ĐỒNG",
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
            Text("Chọn Hợp đồng", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedContract,
                onExpandedChange = { expandedContract = !expandedContract }
            ) {
                OutlinedTextField(
                    value = selectedContractId,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Mã Hợp đồng") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedContract) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedContract,
                    onDismissRequest = { expandedContract = false }
                ) {
                    contracts.forEach { contract ->
                        DropdownMenuItem(
                            text = { Text("${contract.contractId} (Phòng ${contract.roomId})") },
                            onClick = {
                                selectedContractId = contract.contractId
                                expandedContract = false
                            }
                        )
                    }
                }
            }

            Text("Chọn Người thuê thêm", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedTenant,
                onExpandedChange = { expandedTenant = !expandedTenant }
            ) {
                OutlinedTextField(
                    value = selectedTenantId,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("ID Người thuê") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedTenant) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedTenant,
                    onDismissRequest = { expandedTenant = false }
                ) {
                    tenants.forEach { tenant ->
                        DropdownMenuItem(
                            text = { Text("${tenant.fullName} (${tenant.userId})") },
                            onClick = {
                                selectedTenantId = tenant.userId
                                expandedTenant = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (selectedContractId.isNotBlank() && selectedTenantId.isNotBlank()) {
                        viewModel.addTenantToContract(selectedContractId, selectedTenantId)
                        Toast.makeText(context, "Đã thêm người thuê vào hợp đồng", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Vui lòng chọn đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Xác nhận Thêm")
            }
        }
    }
}
