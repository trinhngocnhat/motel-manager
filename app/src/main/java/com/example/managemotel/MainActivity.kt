package com.example.managemotel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.ui.theme.ManageMotelTheme
import com.example.managemotel.roles.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManageMotelTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login", // Vào app hiện màn login đầu tiên
        modifier = modifier
    ) {
        // Gọi đến hàm hiển thị ở file LoginScreen.kt
        composable("login") { LoginScreen(navController = navController) }

        // Gọi đến các hàm hiển thị ở file roles/...
        composable("tenant") { TenantScreen(navController = navController) }
        composable("manager") { ManagerScreen(navController = navController) }
        composable("owner") { OwnerScreen(navController = navController) }

        // Màn hình chi tiết
        composable("owner_revenue") { RevenueReportScreen(navController = navController) }
        composable("manager_tasks") { TaskListScreen(navController = navController) }
        composable("tenant_bill") { BillDetailScreen(navController = navController) }
    }
}
