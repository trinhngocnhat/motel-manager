package com.example.managemotel

import android.os.Bundle
import androidx.compose.runtime.remember
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
import com.example.managemotel.roles.owner.ownerGraph
import com.example.managemotel.roles.manager.managerGraph
import com.example.managemotel.roles.tenant.tenantGraph
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
//fun AppNavigation(modifier: Modifier = Modifier) {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "login", // Vào app hiện màn login đầu tiên
//        modifier = modifier
//    ) {
//        // Gọi đến hàm hiển thị ở file LoginScreen.kt
//        composable("login") { LoginScreen(navController = navController) }
//
//        // Gọi đến các hàm hiển thị theo vai trò (Roles)
//        tenantGraph(navController = navController)
//        managerGraph(navController = navController)
//        ownerGraph(navController = navController)
//    }
//}
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val context = androidx.compose.ui.platform.LocalContext.current

    val sessionManager = remember {
        com.example.managemotel.local.SessionManager(context)
    }

    val startDestination =
        if (sessionManager.isLoggedIn()) {

            when (sessionManager.getRole()?.lowercase()) {
                "owner" -> "owner"
                "manager" -> "manager"
                "tenant" -> "tenant"
                else -> "login"
            }

        } else {
            "login"
        }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable("login") {
            LoginScreen(navController = navController)
        }

        tenantGraph(navController = navController)
        managerGraph(navController = navController)
        ownerGraph(navController = navController)
    }
}