package com.example.managemotel

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.managemotel.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimensions.PaddingScreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Icon / Logo
        Surface(
            modifier = Modifier.size(100.dp),
            color = AppPrimaryBlue.copy(alpha = 0.1f),
            shape = RoundedCornerShape(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.HomeWork, 
                    contentDescription = null, 
                    tint = AppPrimaryBlue, 
                    modifier = Modifier.size(56.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimensions.SpacingHuge))

        Text(
            text = "M-MOTEL", 
            fontSize = AppTypography.SizeExtraLarge, 
            fontWeight = FontWeight.Black, 
            color = AppPrimaryBlue
        )
        
        Text(
            text = "Giải pháp quản lý nhà trọ thông minh", 
            fontSize = AppTypography.SizeNormal, 
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingHuge))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Tên đăng nhập") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingExtraLarge))

        Button(
            onClick = {
                val user = username.trim()
                val pass = password.trim()

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                when {
                    user == "owner" && pass == "123" -> navController.navigate("owner") { popUpTo("login") { inclusive = true } }
                    user == "manager" && pass == "123" -> navController.navigate("manager") { popUpTo("login") { inclusive = true } }
                    user == "tenant" && pass == "123" -> navController.navigate("tenant") { popUpTo("login") { inclusive = true } }
                    else -> Toast.makeText(context, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryBlue)
        ) {
            Text("ĐĂNG NHẬP", fontSize = AppTypography.SizeLarge, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))
        
        TextButton(onClick = {}) {
            Text("Quên mật khẩu?", color = AppPrimaryBlue)
        }
    }
}
