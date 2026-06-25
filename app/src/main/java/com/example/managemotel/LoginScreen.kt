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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.managemotel.domain.model.LoginUiState
import com.example.managemotel.ui.theme.*
import com.example.managemotel.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Observe UI State
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                val user = (uiState as LoginUiState.Success).user
                navigateBasedOnRole(navController, user.role.name)
            }
            is LoginUiState.Error -> {
                val message = (uiState as LoginUiState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    LoginScreenContent(
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        isLoading = uiState is LoginUiState.Loading,
        onLoginClick = {
            val emailVal = email.trim()
            val passVal = password.trim()

            if (emailVal.isEmpty() || passVal.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@LoginScreenContent
            }
            viewModel.login(emailVal, passVal)
        }
    )
}

@Composable
fun LoginScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isLoading: Boolean,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppDimensions.PaddingScreen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("PassWord") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(AppDimensions.RadiusLarge),
            singleLine = true,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppDimensions.SpacingExtraLarge))

        Button(
            onClick = onLoginClick,
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

private fun navigateBasedOnRole(navController: NavController, role: String) {
    val dest = when (role.lowercase()) {
        "owner" -> "owner"
        "manager" -> "manager"
        "tenant" -> "tenant"
        else -> null
    }
    if (dest != null) {
        navController.navigate(dest) {
            popUpTo("login") { inclusive = true }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        email = "",
        onEmailChange = {},
        password = "",
        onPasswordChange = {},
        isLoading = false,
        onLoginClick = {}
    )
}
