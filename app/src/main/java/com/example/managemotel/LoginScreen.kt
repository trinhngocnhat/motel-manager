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
import com.example.managemotel.models.LoginRequest
import com.example.managemotel.network.RetrofitClient
import com.example.managemotel.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
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
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingExtraLarge))

        Button(
            onClick = {
                val emailVal = email.trim()
                val passVal = password.trim()

                if (emailVal.isEmpty() || passVal.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                scope.launch {
                    isLoading = true
                    try {
                        // Using email field to map to the 'email' key in LoginRequest
                        val response = RetrofitClient.instance.login(LoginRequest(emailVal, passVal))
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            val role = loginResponse?.user?.role?.lowercase()
                            
                            when (role) {
                                "owner" -> navController.navigate("owner") { popUpTo("login") { inclusive = true } }
                                "manager" -> navController.navigate("manager") { popUpTo("login") { inclusive = true } }
                                "tenant" -> navController.navigate("tenant") { popUpTo("login") { inclusive = true } }
                                else -> Toast.makeText(context, "Vai trò không hợp lệ: $role", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Lỗi kết nối: ${e.message}", Toast.LENGTH_SHORT).show()
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            colors = ButtonDefaults.buttonColors(containerColor = AppPrimaryBlue),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("ĐĂNG NHẬP", fontSize = AppTypography.SizeLarge, fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))
        
        TextButton(onClick = {}, enabled = !isLoading) {
            Text("Quên mật khẩu?", color = AppPrimaryBlue)
        }
    }
}
