package com.example.managemotel.roles.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerRoomTenantListScreen(
    navController: NavController,
    viewModel: OwnerViewModel = viewModel()
) {
    val rooms by viewModel.allRooms.collectAsState()
    val contracts by viewModel.allContracts.collectAsState()
    
    var selectedRoomId by remember { mutableStateOf("") }
    var expandedRoom by remember { mutableStateOf(false) }
    
    // Filter contracts for the selected room
    val filteredContracts = contracts.filter { it.roomId == selectedRoomId }
    var selectedContractId by remember { mutableStateOf("") }
    var expandedContract by remember { mutableStateOf(false) }

    // State for tenants of the selected contract
    val contractTenants by if (selectedContractId.isNotBlank()) {
        viewModel.getTenantsForContract(selectedContractId).collectAsState(initial = emptyList())
    } else {
        remember { mutableStateOf(emptyList()) }
    }

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "DANH SÁCH NGƯỜI Ở THEO PHÒNG",
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
            Text("1. Chọn Phòng", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expandedRoom,
                onExpandedChange = { expandedRoom = !expandedRoom }
            ) {
                OutlinedTextField(
                    value = selectedRoomId,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Mã Phòng") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedRoom) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedRoom,
                    onDismissRequest = { expandedRoom = false }
                ) {
                    rooms.forEach { room ->
                        DropdownMenuItem(
                            text = { Text("Phòng ${room.roomId} (${room.status})") },
                            onClick = {
                                selectedRoomId = room.roomId
                                selectedContractId = "" // Reset contract
                                expandedRoom = false
                            }
                        )
                    }
                }
            }

            if (selectedRoomId.isNotBlank()) {
                Text("2. Chọn Hợp đồng", style = MaterialTheme.typography.titleMedium)
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
                        filteredContracts.forEach { contract ->
                            DropdownMenuItem(
                                text = { Text("${contract.contractId} (${contract.startDate} - ${contract.endDate})") },
                                onClick = {
                                    selectedContractId = contract.contractId
                                    expandedContract = false
                                }
                            )
                        }
                    }
                }
            }

            if (selectedContractId.isNotBlank()) {
                SectionTitle(title = "Thông tin Người ở")
                
                val currentContract = contracts.find { it.contractId == selectedContractId }
                Text("Người thuê chính: ${currentContract?.userId ?: "---"}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(8.dp))
                Text("Danh sách người ở cùng:", style = MaterialTheme.typography.titleSmall)
                
                val headers = listOf("ID", "Họ tên", "Số điện thoại")
                val rows = contractTenants.map { tenant ->
                    listOf(tenant.userId, tenant.fullName, tenant.phone ?: "---")
                }

                if (rows.isNotEmpty()) {
                    MotelTable(headers = headers, rows = rows)
                } else {
                    Text("Chưa có thêm người ở nào trong hợp đồng này.")
                }
            }
        }
    }
}
