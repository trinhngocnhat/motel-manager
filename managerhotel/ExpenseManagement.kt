package com.example.managemotel.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.*
import com.example.managemotel.ui.theme.*
import java.util.Locale

data class ExpenseItem(
    val id: String,
    val title: String,
    val category: String,
    val amount: Long,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseManagementScreen(navController: NavController) {
    // SQLITE: Dữ liệu này sẽ được lấy từ bảng 'expenses' trong database
    val totalExpense = 12500000L
    
    val expenses = listOf(
        ExpenseItem("1", "Trả tiền điện tổng tháng 05", "Điện nước", 8500000L, "05/06/2024"),
        ExpenseItem("2", "Thay vòi hoa sen phòng 201", "Sửa chữa", 250000L, "08/06/2024"),
        ExpenseItem("3", "Tiền rác & Vệ sinh chung", "Dịch vụ", 1200000L, "10/06/2024"),
        ExpenseItem("4", "Mua thêm 2 bình cứu hỏa", "Mua sắm", 1800000L, "12/06/2024"),
        ExpenseItem("5", "Sửa bóng đèn hành lang", "Sửa chữa", 150000L, "14/06/2024")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("QUẢN LÝ CHI PHÍ", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppPrimaryBlue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Mở form thêm chi phí */ },
                containerColor = AppPrimaryBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm chi phí")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tổng chi
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Tổng chi phí tháng này", color = Color(0xFFC62828), fontSize = 14.sp)
                        Text(
                            text = formatCurrency(totalExpense),
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color(0xFFC62828)
                        )
                    }
                }
            }

            item {
                SectionTitle(title = "Lịch sử chi tiêu")
            }

            items(expenses) { expense ->
                ExpenseCard(expense)
            }
            
            item { Spacer(modifier = Modifier.height(70.dp)) } // Cách ra để không bị FAB che
        }
    }
}

@Composable
fun ExpenseCard(expense: ExpenseItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expense.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = AppPrimaryBlue.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = expense.category,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = AppPrimaryBlue,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = expense.date,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
            Text(
                text = "-${formatCurrency(expense.amount)}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC62828),
                fontSize = 16.sp
            )
        }
    }
}

private fun formatCurrency(amount: Long): String {
    return String.format(Locale.US, "%,dđ", amount)
}

@Preview(showBackground = true)
@Composable
fun ExpenseManagementPreview() {
    ExpenseManagementScreen(rememberNavController())
}
