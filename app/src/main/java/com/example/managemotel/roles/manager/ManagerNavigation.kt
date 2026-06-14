package com.example.managemotel.roles.manager
// Hoặc package manager của bạn

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
//import com.example.managemotel.roles.ManagerScreen
//import com.example.managemotel.roles.TaskListScreen
// Import các màn hình CRUD khác của Manager nếu có

// Hàm mở rộng để thêm biểu đồ điều hướng của Manager vào NavHost chính
fun NavGraphBuilder.managerGraph(navController: NavController) {
    navigation(
        startDestination = "manager_home", // Màn hình đầu tiên khi vào role Manager
        route = "manager"            // Tên định danh cho toàn bộ nhóm màn hình này
    ) {
        // 1. Màn hình chính của Manager
        composable("manager_home") {
            ManagerScreen(navController = navController)
        }

        // 2. Các màn hình con do Manager tự quyết định
        composable("room_management") {
           RoomsManagementScreen(navController = navController)
        }

        composable("tenant_management") {
            TenantsManagementScreen(navController = navController)
        }

        composable("manager_tasks") {
            TaskListScreen(navController = navController)
        }

        // Sau này có thêm màn hình nào của Manager, cứ thêm vào đây
    }
}