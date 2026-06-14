package com.example.managemotel.roles.owner

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.ownerGraph(navController: NavController) {
    navigation(
        startDestination = "owner_home",
        route = "owner"
    ) {
        composable("owner_home") {
            OwnerScreen(navController = navController)
        }
        composable("owner_revenue") {
            RevenueReportScreen(navController = navController)
        }
        composable("owner_room_management") {
            OwnerRoomsManagementScreen(navController = navController)
        }
        composable("owner_tenant_management") {
            OwnerAccountManagementScreen(navController = navController)
        }
        composable("owner_issues_management") {
            OwnerIssueManagementScreen(navController = navController)
        }
        composable("owner_revenue_management") {
            OwnerRevenueManagementScreen(navController = navController)
        }
        composable("owner_tasks") {
            OwnerTaskListScreen(navController = navController)
        }
        composable("owner_contract_management") {
            OwnerContractManagementScreen(navController = navController)
        }
        composable("owner_add_tenant_to_contract") {
            OwnerAddTenantToContractScreen(navController = navController)
        }
        composable("owner_room_tenant_list") {
            OwnerRoomTenantListScreen(navController = navController)
        }
    }
}
