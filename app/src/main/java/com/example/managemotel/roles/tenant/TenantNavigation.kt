package com.example.managemotel.roles.tenant

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.tenantGraph(navController: NavController) {
    navigation(
        startDestination = "tenant_home",
        route = "tenant"
    ) {
        composable("tenant_home") {
            TenantScreen(navController = navController)
        }
        composable("request") {
            RequestScreen(navController = navController)
        }
        composable("notification") {
            NotificationScreen(navController = navController)
        }
    }
}
